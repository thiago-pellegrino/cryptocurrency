package br.com.cryptocurrency.domain;

import br.com.cryptocurrency.enums.EnumNodeType;
import br.com.cryptocurrency.enums.EnumStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Node {

	private String id;
	private long timestamp;
	private String url;
	private EnumStatus status;
	private EnumNodeType nodeType;

}
