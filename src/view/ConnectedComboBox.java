package view;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ConnectedComboBox<T> implements ChangeListener<T> {

    private ObservableList<T> items;
    private List<ComboBox<T>> comboBoxList = new ArrayList<>();

    public ConnectedComboBox(ObservableList<T> items){
        this.items = items;
        if (this.items == null) this.items = FXCollections.observableArrayList();
    }

    public void addComboBox(ComboBox<T> comboBox){
        comboBoxList.add(comboBox);
        comboBox.valueProperty().addListener(this);
        updateSelection();
    }

    public void removeComboBox(ChoiceBox<T> comboBox){
        comboBoxList.remove(comboBox);
        comboBox.valueProperty().removeListener(this);
        updateSelection();
    }

    // this boolean needed because we can set combobox Value in updateSelection()
    // this will trigger a value listener and update selection one more time => stack overflow
    // this behavior occurs only if we have more than one equal item in source ObservableList<T> items list.
    private boolean updating = false;
    private void updateSelection() {
        if (updating) return;
        updating = true;

        List<T> availableChoices = items.stream().collect(Collectors.toList());
        for (ComboBox<T> comboBox: comboBoxList){
            if (comboBox.getValue()!= null) {
                availableChoices.remove(comboBox.getValue());
            }
        }

        for (ComboBox<T> comboBox: comboBoxList){
            T selectedValue = comboBox.getValue();
            ObservableList<T> items = comboBox.getItems();
            items.setAll(availableChoices);

            if (selectedValue != null) {
                items.add(selectedValue);
                comboBox.setValue(selectedValue);
            }
        }
        updating = false;
    }

    @Override
    public void changed(ObservableValue<? extends T> observable, T oldValue, T newValue) {
        Platform.runLater(() -> updateSelection() );
        //updateSelection();
    }
}