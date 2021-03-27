package com.db.assignment.demo.rs;

import java.text.ParseException;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.db.assignment.demo.dto.TradeInfoDto;
import com.db.assignment.demo.ehs.InvalidVersionException;
import com.db.assignment.demo.service.Validation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class TradeRequestController {
	private static final Logger log = LoggerFactory.getLogger(TradeRequestController.class);
	@Autowired
	private Validation validation;
	private ObjectMapper om = new ObjectMapper();
	public Map<String, Map<Integer,TradeInfoDto>> trades = new HashMap<String, Map<Integer,TradeInfoDto>>();
	@PostMapping(path="/ws/trade", consumes = "application/json")
	public ResponseEntity<String> storeTradeInfo(@RequestBody TradeInfoDto tradeInfoDto) throws JsonProcessingException {
		/*
		 * Validate maturity date
		 */
		if(!validation.validateMaturityDate(tradeInfoDto))
			return ResponseEntity.status(HttpStatus.FORBIDDEN).headers(new HttpHeaders()).body("ALERT!Invalid maturity date i.e. maturitydt<todaysdate");
		/*
		 * Validate trade version
		 */
		try {
			validation.validateLowerVersion(tradeInfoDto, trades);
		} catch (InvalidVersionException e) {
			// TODO Auto-generated catch block
			return ResponseEntity.status(HttpStatus.FORBIDDEN).headers(new HttpHeaders()).body(e.getMessage());
		}

		/*
		 *  If trade already exist in the map then 
		 *  add new version details in the child map 
		 *  else create a new childmap
		 */
		Map<Integer,TradeInfoDto> tradeVerMap = trades.get(tradeInfoDto.getTradeId());
		if(tradeVerMap==null) {
			tradeVerMap = new HashMap<Integer, TradeInfoDto>();
		} 
		tradeVerMap.put(tradeInfoDto.getVersion(), tradeInfoDto);
		trades.put(tradeInfoDto.getTradeId(), tradeVerMap);
		return ResponseEntity.status(HttpStatus.OK).headers(new HttpHeaders()).body(om.writeValueAsString(tradeInfoDto)) ;
	}
	
	@GetMapping(path="/ws/trade", produces = "application/json")
	public String getTradeList() throws JsonProcessingException, ParseException {
		return om.writeValueAsString(trades) ;
	}
	
	@DeleteMapping(path="/ws/trade")
	public void deleteTrades() throws JsonProcessingException, ParseException {
		trades=new HashMap<String, Map<Integer,TradeInfoDto>>();
	}
	
	@Scheduled(initialDelay = 60000L, fixedDelayString = "PT1M")
	private void updateExpireFlag() {
		log.info("Schedule job running...");
		System.out.println("Schedule job running..."+Instant.now());
			for (Map.Entry<String, Map<Integer,TradeInfoDto>> entry : trades.entrySet()) {
				Map<Integer, TradeInfoDto> tradeVersionMap = entry.getValue();
				if(tradeVersionMap!=null) {
					for (Map.Entry<Integer, TradeInfoDto> childEntry : tradeVersionMap.entrySet()) {
						if(childEntry.getValue().getMaturityDt().before(new Date()))
							childEntry.getValue().setExpired("Y");
					} 
				}
			} 
		}
	}

