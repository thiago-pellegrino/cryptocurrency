package br.com.cryptocurrency.nodenetwork.core.service.network;

import java.util.List;

import org.springframework.stereotype.Component;

import br.com.cryptocurrency.domain.Node;

@Component
public interface NetworkService {

	/**
	 * 
	 */
	public List<Node> getNodes();

}
