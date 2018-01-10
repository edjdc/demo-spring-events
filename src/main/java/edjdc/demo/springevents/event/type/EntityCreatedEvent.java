package edjdc.demo.springevents.event.type;

import org.springframework.context.ApplicationEvent;
import org.springframework.core.ResolvableType;
import org.springframework.core.ResolvableTypeProvider;

public class EntityCreatedEvent<T> extends ApplicationEvent implements ResolvableTypeProvider {

	private static final long serialVersionUID = -40388876694155487L;

	private T entity;
	
	public EntityCreatedEvent(T entity) {
		super(entity);
		this.entity = entity;
	}

	@Override
	public ResolvableType getResolvableType() {
		return ResolvableType.forClassWithGenerics(getClass(), ResolvableType.forInstance(getSource()));
	}
	
	public T getEntity() {
		return entity;
	}

}
