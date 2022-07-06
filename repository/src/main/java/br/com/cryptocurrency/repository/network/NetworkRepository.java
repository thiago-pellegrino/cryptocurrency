package br.com.cryptocurrency.repository.network;

import java.util.List;

import org.springframework.stereotype.Component;

import br.com.cryptocurrency.domain.Node;

@Component
public interface NetworkRepository {

	public List<Node> getNodes();
}
