package org.jabref.gui.importer;

import java.util.List;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.VBox;

import org.jabref.gui.util.BaseDialog;
import org.jabref.logic.l10n.Localization;
import org.jabref.model.database.BibDatabaseMode;
import org.jabref.model.entry.EntryType;

import com.airhacks.afterburner.views.ViewLoader;
import org.controlsfx.control.CheckListView;

public class ImportCustomEntryTypesDialog extends BaseDialog<Void> {

    @FXML private CheckListView<EntryType> unknownEntryTypesCheckList;
    @FXML private VBox boxDifferentCustomization;
    @FXML private CheckListView<EntryType> differentCustomizationCheckList;

    private ImportCustomEntryTypesDialogViewModel viewModel;

    private final BibDatabaseMode mode;
    private final List<EntryType> customEntryTypes;

    public ImportCustomEntryTypesDialog(BibDatabaseMode mode, List<EntryType> customEntryTypes) {
        this.mode = mode;
        this.customEntryTypes = customEntryTypes;

        ViewLoader.view(this)
                  .load()
                  .setAsDialogPane(this);

        setResultConverter(btn -> {
            if (btn == ButtonType.OK) {
                viewModel.importCustomEntryTypes(unknownEntryTypesCheckList.getCheckModel().getCheckedItems(), differentCustomizationCheckList.getCheckModel().getCheckedItems());
            }
            return null;
        });

        setTitle(Localization.lang("Custom entry types"));

    }

    @FXML
    public void initialize() {
        viewModel = new ImportCustomEntryTypesDialogViewModel(mode, customEntryTypes);

        boxDifferentCustomization.managedProperty().bind(Bindings.isNotEmpty(viewModel.differentCustomizationsProperty()));
        unknownEntryTypesCheckList.itemsProperty().bind(viewModel.newTypesProperty());
        differentCustomizationCheckList.itemsProperty().bind(viewModel.differentCustomizationsProperty());
    }

}
