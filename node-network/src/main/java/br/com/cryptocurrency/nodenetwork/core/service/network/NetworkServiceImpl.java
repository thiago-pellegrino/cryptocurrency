package br.com.cryptocurrency.nodenetwork.core.service.network;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.cryptocurrency.domain.Node;
import br.com.cryptocurrency.repository.network.NetworkRepository;

@Component
public class NetworkServiceImpl implements NetworkService {

	@Autowired
	private NetworkRepository networkRepository;

	/**
	 * 
	 */
	@Override
	public List<Node> getNodes() {
		return networkRepository.getNodes();
	}

}