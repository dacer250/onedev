package com.pmease.commons.wicket.editable;

import java.io.Serializable;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;

import com.pmease.commons.editable.PropertyDescriptor;
import com.pmease.commons.editable.PropertyDescriptorImpl;
import com.pmease.commons.loader.AppLoader;

@SuppressWarnings("serial")
public abstract class PropertyContext<T> extends PropertyDescriptorImpl {

	public PropertyContext(Class<?> beanClass, String propertyName) {
		super(beanClass, propertyName);
	}
	
	public PropertyContext(PropertyDescriptor propertyDescriptor) {
		super(propertyDescriptor);
	}

	public abstract Component renderForView(String componentId, IModel<T> model);

	public abstract PropertyEditor<T> renderForEdit(String componentId, IModel<T> model);

	public static PropertyEditor<Serializable> edit(String componentId, final IModel<Serializable> beanModel, String propertyName) {
		final PropertyContext<Serializable> editContext = of(beanModel.getObject().getClass(), propertyName);
		return editContext.renderForEdit(componentId, new IModel<Serializable>() {

			@Override
			public void detach() {
				beanModel.detach();
			}

			@Override
			public Serializable getObject() {
				return (Serializable) editContext.getPropertyValue(beanModel.getObject());
			}

			@Override
			public void setObject(Serializable object) {
				editContext.setPropertyValue(beanModel.getObject(), object);
			}
			
		});
	}

	public static PropertyEditor<Object> edit(String componentId, final Serializable bean, String propertyName) {
		IModel<Object> beanModel = new IModel<Object>() {

			@Override
			public void detach() {
			}

			@Override
			public Object getObject() {
				return bean;
			}

			@Override
			public void setObject(Object object) {
				throw new IllegalStateException();
			}
			
		};
		return edit(componentId, beanModel, propertyName);
	}

	public static Component view(String componentId, final IModel<Serializable> beanModel, String propertyName) {
		final PropertyContext<Serializable> editContext = of(beanModel.getObject().getClass(), propertyName);
		return editContext.renderForView(componentId, new LoadableDetachableModel<Serializable>() {

			@Override
			protected Serializable load() {
				return (Serializable) editContext.getPropertyValue(beanModel.getObject());
			}
			
		});
	}

	public static Component view(String componentId, Serializable bean, String propertyName) {
		return view(componentId, Model.of(bean), propertyName);
	}

	public static PropertyContext<Serializable> of(Class<?> beanClass, String propertyName) {
		EditSupportRegistry registry = AppLoader.getInstance(EditSupportRegistry.class);
		return registry.getPropertyEditContext(beanClass, propertyName);
	}
	
	public static PropertyContext<Serializable> of(PropertyDescriptor propertyDescriptor) {
		return of(propertyDescriptor.getBeanClass(), propertyDescriptor.getPropertyName());
	}
}