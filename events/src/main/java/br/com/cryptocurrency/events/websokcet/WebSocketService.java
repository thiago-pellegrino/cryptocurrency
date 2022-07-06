package br.com.cryptocurrency.events.websokcet;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import lombok.extern.slf4j.Slf4j;

@Service("WebSocketService")
@Slf4j
public class WebSocketService extends AbstractWebSocketHandler {

	public static Set<WebSocketSession> peers = Collections.synchronizedSet(new HashSet<WebSocketSession>());

	private final String versao = "[V: 0.0.1-SNAPSHOT] Step: ";

	/**
	 * @param peer
	 */
	@Override
	public void afterConnectionEstablished(WebSocketSession peer) throws Exception {
		peers.add(peer);
		sendMessageTo("Start comunuication.");
	}

	/**
	 * 
	 * @param message
	 */
	public void sendMessageTo(String message) {
		try {
			handleTextMessage(null, new TextMessage(message));
		} catch (InterruptedException e) {
			log.error(versao + "ERROR WEBSOCKET: " + e.getMessage());
		} catch (IOException e) {
			log.error(versao + "ERROR WEBSOCKET: " + e.getMessage());
		}
	}

	/**
	 * @param session
	 * @param message
	 */
	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage textMessage)
			throws InterruptedException, IOException {

		if (session != null) {
			// sendToTopic(session, textMessage);
		}

		if (!peers.isEmpty()) {
			log.info(versao + "SEND TO " + peers.size() + " SESSION(S)");
			for (WebSocketSession peer : peers) {
				try {
					if (peer.isOpen()) {
						peer.sendMessage(textMessage);
					}
				} catch (Exception e) {
					log.error(versao + "ERROR WEBSOCKET: " + e.getMessage());
				}
			}
		} else {
			log.info(versao + "NO SESSIONS ONPENED TO SEND DATA.");
		}

		log.info(versao + "WEBSOCKET FINISHED");
	}
}