package br.com.cryptocurrency.nodenetwork.core.service.node;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.cryptocurrency.domain.Node;
import br.com.cryptocurrency.repository.node.NodeRepository;

@Component
public class NodeServiceImpl implements NodeService {

	@Autowired
	private NodeRepository nodeRepository;

	/**
	 * 
	 */
	@Override
	public Node getNode(String userId) {
		return nodeRepository.getNode(userId);
	}

	/**
	 * 
	 */
	@Override
	public void conectNodeToNework(Node node) {
		nodeRepository.save(node);
	}

}
