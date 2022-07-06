package br.com.cryptocurrency.repository.node;

import org.springframework.stereotype.Component;

import br.com.cryptocurrency.domain.Node;

@Component
public interface NodeRepository {

	/**
	 * 
	 * @param userId
	 * @return
	 */
	public Node getNode(String userId);

	/**
	 * 
	 * @param node
	 */
	public void save(Node node);
}
