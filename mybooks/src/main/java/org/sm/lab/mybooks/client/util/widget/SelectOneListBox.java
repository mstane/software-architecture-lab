package org.sm.lab.mybooks.client.util.widget;

import java.util.ArrayList;
import java.util.Collection;
 
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.ListBox;
 
/**
 * A ListBox that can be populated with any Collection
 * 
 * Published under Apache License v2
 * 
 * @author David Chandler
 * @param <T>
 */
public class SelectOneListBox<T> extends ListBox implements HasSelectedValue<T>
{
 
    public interface OptionFormatter<T>
    {
        abstract String getLabel(T option);
        abstract String getValue(T option);
    }
     
    private boolean valueChangeHandlerInitialized;
    private T[] options;
    private OptionFormatter<T> formatter;
 
    public SelectOneListBox(Collection<T> selections, OptionFormatter<T> formatter)
    {
        setSelections(selections);
        setFormatter(formatter);
    }
 
    public SelectOneListBox(OptionFormatter<T> formatter)
    {
        this(new ArrayList<T>(), formatter);
    }
 
    public void setFormatter(OptionFormatter<T> formatter)
    {
        this.formatter = formatter; 
    }
 
    @SuppressWarnings("unchecked")
    @Override
    public void setSelections(Collection<T> selections)
    {
        // Remove prior options
        if (options != null)
        {
            int numItems = this.getItemCount();
            int firstOption = numItems - options.length;
            for (int i=firstOption; i<numItems; i++)
                this.removeItem(firstOption);
        }
        options = (T[]) selections.toArray();
        for (T option : options)
        {
            String optionLabel = formatter.getLabel(option);
            String optionValue = formatter.getValue(option);
            this.addItem(optionLabel, optionValue);
        }
    }
 
    @Override
    public T getSelectedValue()
    {
        if (getSelectedIndex() >= 0)
        {
            String name = getValue(getSelectedIndex());
 
            for (T option : options)
            {
                if (formatter.getValue(option).equals(name))
                    return option;
            }
        }
 
        return null;
    }
 
    @Override
    public void setSelectedValue(T value)
    {
        if (value == null)
            return;
         
        for (int i=0; i < this.getItemCount(); i++)
        {
            String thisLabel = this.getItemText(i);
            if (formatter.getLabel(value).equals(thisLabel))
            {
                this.setSelectedIndex(i);
                return;
            }
        }
        throw new IllegalArgumentException("No index found for value " + value.toString());
    }
 
    /*
     * Methods to implement HasValue 
     */
     
    @Override
    public T getValue()
    {
        return this.getSelectedValue();
    }
 
    @Override
    public void setValue(T value)
    {
        this.setValue(value, false);
    }
 
    @Override
    public void setValue(T value, boolean fireEvents)
    {
        T oldValue = getValue();
        this.setSelectedValue(value);
        if (fireEvents)
        {
            ValueChangeEvent.fireIfNotEqual(this, oldValue, value);
        }
    }
 
    @Override
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<T> handler)
    {
        // Initialization code
        if (!valueChangeHandlerInitialized)
        {
            valueChangeHandlerInitialized = true;
            super.addChangeHandler(new ChangeHandler()
            {
                public void onChange(ChangeEvent event)
                {
                    ValueChangeEvent.fire(SelectOneListBox.this, getValue());
                }
            });
        }
        return addHandler(handler, ValueChangeEvent.getType());
    }
    
    @Override
    public void clear() {
    	super.clear();
    	options = null;
    }
 
}
