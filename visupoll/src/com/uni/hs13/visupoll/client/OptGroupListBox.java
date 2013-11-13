/*
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.uni.hs13.visupoll.client;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.OptGroupElement;
import com.google.gwt.dom.client.OptionElement;
import com.google.gwt.dom.client.SelectElement;
import com.google.gwt.user.client.ui.ListBox;

/**
 * This Class extends the {@link ListBox} and adds the possibility to use the <em>optgroup</em>
 * -HTML-tag. To add and remove elements, please use only the methods defined in this class.
 * 
 * @author Denny Kluge
 * 
 */
public class OptGroupListBox extends ListBox {

  public enum OptGroupListBoxItemType {
    GROUP("OPTGROUP"), OPTION("OPTION");

    private String itemNodeName;

    private OptGroupListBoxItemType(String itemNodeName) {
      this.itemNodeName = itemNodeName;
    }

    public String getItemNodeName() {
      return itemNodeName;
    }
  }

  private OptGroupElement latestOptGroupElement;

  /**
   * Adds a new OPTGROUP item to the ListBox. If the name of the group already exists, the group
   * item will <strong>not</strong> be created.
   * 
   * @param groupName The name of the group.
   * @return {@code true}, if the name does not already exist, otherwise {@code false}.
   */
  public boolean addGroup(final String groupName) {
    // check if this group already exists
    SelectElement selectElement = this.getElement().cast();
    NodeList<Element> elementsByTagName = selectElement.getElementsByTagName("*");

    for (int i = 0; i < elementsByTagName.getLength(); i++) {
      Element item = elementsByTagName.getItem(i);

      if (OptGroupListBoxItemType.GROUP.getItemNodeName().equals(item.getNodeName())) {
        OptGroupElement castedElement = (OptGroupElement) item;
        if (castedElement.getLabel().equals(groupName)) {
          return false;
        }
      }
    }

    // okay, it does not already exist... create it
    OptGroupElement groupElement = Document.get().createOptGroupElement();
    groupElement.setLabel(groupName);
    SelectElement select = this.getElement().cast();
    select.appendChild(groupElement);
    latestOptGroupElement = groupElement;
    return true;
  }

  /**
   * Adds a new OPTION item to the ListBox. Use this method if key and label of the item are the
   * same.<br>
   * If the <em>key</em> of this item already exists, the item will <strong>not</strong> be created
   * (existing <em>label</em> is fine, though). Otherwise it will be added to the last created
   * OPTGROUP item. If no OPTGROUP item exists, the OPTION item will be created without a OPTGROUP
   * parent item.
   * 
   * @param keyLabelName the key and the label (= equal values) of the OPTION item
   * @return {@code true}, if the key of the item does not already exist, otherwise {@code false}.
   */
  public boolean addGroupItem(final String keyLabelName) {
    return addGroupItem(keyLabelName, keyLabelName);
  }

  /**
   * Adds a new OPTION item to the ListBox. If the <em>key</em> of this item already exists, the
   * item will <strong>not</strong> be created (existing <em>label</em> is fine, though). Otherwise
   * it will be added to the last created OPTGROUP item. If no OPTGROUP item exists, the OPTION item
   * will be created without a OPTGROUP parent item.
   * 
   * @param key the key of the OPTION item
   * @param label the label of the OPTION item
   * @return {@code true}, if the key of the item does not already exist, otherwise {@code false}.
   */
  public boolean addGroupItem(final String key, final String label) {
    // check if this item already exists
    SelectElement selectElement = this.getElement().cast();
    NodeList<Element> elementsByTagName = selectElement.getElementsByTagName("*");

    for (int i = 0; i < elementsByTagName.getLength(); i++) {
      Element item = elementsByTagName.getItem(i);

      if (OptGroupListBoxItemType.OPTION.getItemNodeName().equals(item.getNodeName())) {
        OptionElement castedElement = (OptionElement) item;
        if (castedElement.getValue().equals(key)) {
          return false;
        }
      }
    }

    // okay, it does not already exist... create it
    if (latestOptGroupElement == null) {
      this.addItem(label, key);
    } else {
      OptionElement optElement = Document.get().createOptionElement();
      optElement.setInnerText(label);
      optElement.setValue(key);
      latestOptGroupElement.appendChild(optElement);
    }
    return true;
  }

  /**
   * Removes an item at the given index from the ListBox. The index starts at 0.
   * 
   * @param index See description.
   * @return {@code true}, if an item with the given index was found (and removed), otherwise
   *         {@code false}.
   */
  public boolean removeItemAtIndex(int index) {
    SelectElement selectElement = this.getElement().cast();
    NodeList<Element> elementsByTagName = selectElement.getElementsByTagName("*");
    Element item = elementsByTagName.getItem(index);

    if (item == null) {
      return false;
    }
    item.removeFromParent();

    return true;
  }

  /**
   * Removes an item with the given key from the ListBox.
   * 
   * @param type One of the two types defined in {@link OptGroupListBoxItemType}, namely a
   *          <em>group</em> or a <em>group item</em>.
   * @param key If <em>type</em> is a <em>group item</em>, then <em>key</em> has to be the unique
   *          key of the ListBox element. If <em>type</em> is a <em>group</em>, then <em>key</em>
   *          has to be the label of this group. Pay attention here, as group labels may be present
   *          multiple times (non-unique). This methods only deletes the first occurrence of the
   *          <em>group item</em>.
   * @return {@code true}, if an item with the given key was found (and removed), otherwise
   *         {@code false}.
   */
  public boolean removeItemWithKey(OptGroupListBoxItemType type, String key) {
    SelectElement selectElement = this.getElement().cast();
    NodeList<Element> elementsByTagName = selectElement.getElementsByTagName("*");

    for (int i = 0; i < elementsByTagName.getLength(); i++) {
      Element item = elementsByTagName.getItem(i);

      if ((OptGroupListBoxItemType.OPTION.equals(type)
          && OptGroupListBoxItemType.OPTION.getItemNodeName().equals(item.getNodeName()) && ((OptionElement) item)
          .getValue().equals(key))
          || (OptGroupListBoxItemType.GROUP.equals(type)
              && OptGroupListBoxItemType.GROUP.getItemNodeName().equals(item.getNodeName()) && ((OptGroupElement) item)
              .getLabel().equals(key))) {
        item.removeFromParent();
        return true;
      }
    }

    return false;
  }

  /**
   * Returns the size of this ListBox, namely the count of elements, including group-elements.
   * 
   * @return See description.
   */
  public int getSize() {
    SelectElement selectElement = this.getElement().cast();
    NodeList<Element> elementsByTagName = selectElement.getElementsByTagName("*");

    return elementsByTagName.getLength();
  }
}