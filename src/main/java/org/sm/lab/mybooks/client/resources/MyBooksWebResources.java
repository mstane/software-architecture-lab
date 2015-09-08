package org.sm.lab.mybooks.client.resources;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.CssResource.NotStrict;
import com.google.gwt.resources.client.ImageResource;

/**
 * The images and styles used throughout the MyBooks.
 */
public interface MyBooksWebResources extends ClientBundle {
  ImageResource catI18N();

  ImageResource catLists();

  ImageResource catOther();

  ImageResource catPanels();

  ImageResource catPopups();

  ImageResource catTables();

  ImageResource catTextInput();

  ImageResource catWidgets();

  /**
   * The styles used in LTR mode.
   */
  @NotStrict
  @Source("MyBooksWeb.css")
  CssResource css();

  ImageResource logo();

  ImageResource logoThumb();

  ImageResource loading();

  /**
   * Indicates the locale selection box.
   */
  ImageResource locale();
    
  ImageResource book();
  
  ImageResource note();
  
  
  
}