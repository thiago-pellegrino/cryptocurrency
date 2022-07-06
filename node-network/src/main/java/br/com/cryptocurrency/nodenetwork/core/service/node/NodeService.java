package br.com.cryptocurrency.nodenetwork.core.service.node;

import org.springframework.stereotype.Component;

import br.com.cryptocurrency.domain.Node;

@Component
public interface NodeService {

	/**
	 * 
	 */
	public Node getNode(String userId);

	/**
	 * 
	 */
	public void conectNodeToNework(Node node);

}
