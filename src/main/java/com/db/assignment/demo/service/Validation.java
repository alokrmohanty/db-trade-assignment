package com.db.assignment.demo.service;

import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.db.assignment.demo.dto.TradeInfoDto;
import com.db.assignment.demo.ehs.InvalidVersionException;

@Service
public class Validation {
	public boolean validateMaturityDate(TradeInfoDto tradeInfoDto) {
		if(tradeInfoDto.getMaturityDt().before(new Date())) //to be changed to new Date() //tradeInfoDto.getCreatedDt()
			return false;
		else
			return true;
	}
	
	public void validateLowerVersion(TradeInfoDto tradeInfoDto,Map<String, Map<Integer,TradeInfoDto>> trades) throws InvalidVersionException {
		Map<Integer, TradeInfoDto> tradeVersionMap = trades.get(tradeInfoDto.getTradeId());
		if(tradeVersionMap!=null) {
			for (Map.Entry<Integer, TradeInfoDto> entry : tradeVersionMap.entrySet()) {
				if(tradeInfoDto.getVersion()<entry.getKey())
					throw new InvalidVersionException("ALERT!Trade version supplied cannot be less than the existing trade version");
			} 
		}
	}
	
}
