package br.com.cryptocurrency.domain;

import java.util.List;

import javax.persistence.Id;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Network {

	@Id
	private String id;

	private List<Node> nodes;

}
