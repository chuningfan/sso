package sso.core.internal;

import sso.core.internal.dto.Result;
import sso.core.internal.dto.SSORequest;

public class ChainHandler<T, P extends SSORequest, R extends Result<T>> {
	
	private AbstractAuthHandler<T, P, R> head;
	
	private AbstractAuthHandler<T, P, R> tail;
	
	public void addNode(AbstractAuthHandler<T, P, R> node) {
		if (head == null && tail == null) {
			head = node;
			tail = node;
		} else {
			tail.nextHandler = node;
			tail = node;
		}
	}
	
	public R start(P packet) {
		return head.process(packet);
	}
	
}
