package sso.core.internal.handler;

import sso.core.internal.dto.Result;
import sso.core.internal.dto.SSORequest;

public class ChainHandler<T, P extends SSORequest, R extends Result<T>> {
	
	private AbstractAuthHandler<T, P, R> head;
	
	private AbstractAuthHandler<T, P, R> tail;
	
	public ChainHandler<T, P, R> addNode(AbstractAuthHandler<T, P, R> node) {
		if (head == null && tail == null) {
			head = node;
			tail = node;
		} else {
			tail.nextHandler = node;
			tail = node;
		}
		return this;
	}
	
	public R start(P packet, R result) throws Exception {
		return head.process(packet, result);
	}
	
}
