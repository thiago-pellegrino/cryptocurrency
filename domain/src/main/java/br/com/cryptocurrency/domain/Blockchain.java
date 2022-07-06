package br.com.cryptocurrency.domain;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Id;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Blockchain {

	@Id
	private String id;	
	private long timestamp;
	private List<Block> chain;		
	private BigDecimal sizeOfData;
	private BigDecimal minerAverage;

}
