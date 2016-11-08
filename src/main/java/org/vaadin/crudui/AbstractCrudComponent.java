package org.vaadin.crudui;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.CustomComponent;
import org.vaadin.crudui.impl.form.AutoGeneratedVerticalCrudFormBuilder;
import org.vaadin.crudui.impl.layout.VerticalCrudLayout;

import java.util.function.Consumer;

/**
 * @author Alejandro Duarte
 */
public abstract class AbstractCrudComponent<T> extends CustomComponent implements CrudComponent<T> {

    protected Class<T> domainType;
    protected Object[] newFormVisiblePropertyIds;
    protected Object[] editFormVisiblePropertyIds;
    protected Object[] deleteFormVisiblePropertyIds;
    protected String[] newFormFieldCaptions;
    protected String[] editFormFieldCaptions;
    protected String[] deleteFormFieldCaptions;
    protected Object[] editFormDisabledPropertyIds;

    protected Consumer<T> add = t -> { };
    protected Consumer<T> update = t -> { };
    protected Consumer<T> delete = t -> { };

    protected CrudLayout mainLayout;
    protected CrudFormBuilder<T> crudFormBuilder;

    public AbstractCrudComponent(Class<T> domainType) {
        this(domainType, new VerticalCrudLayout());
    }

    public AbstractCrudComponent(Class<T> domainType, CrudLayout mainLayout) {
        this.domainType = domainType;
        this.mainLayout = mainLayout;
        BeanItemContainer<T> propertyIdsHelper = new BeanItemContainer<T>(domainType);
        crudFormBuilder = new AutoGeneratedVerticalCrudFormBuilder<T>();
        newFormVisiblePropertyIds = propertyIdsHelper.getContainerPropertyIds().toArray();
        editFormVisiblePropertyIds = propertyIdsHelper.getContainerPropertyIds().toArray();
        deleteFormVisiblePropertyIds = propertyIdsHelper.getContainerPropertyIds().toArray();

        setCompositionRoot(mainLayout);
        setSizeFull();
    }

    @Override
    public void setCaption(String caption) {
        mainLayout.setCaption(caption);
    }

    @Override
    public void showAllOptions() {
        showAddOption();
        showEditOption();
        showDeleteOption();
    }

    @Override
    public void setNewFormVisiblePropertyIds(Object... newFormVisiblePropertyIds) {
        this.newFormVisiblePropertyIds = newFormVisiblePropertyIds;
    }

    @Override
    public void setEditFormVisiblePropertyIds(Object... editFormVisiblePropertyIds) {
        this.editFormVisiblePropertyIds = editFormVisiblePropertyIds;
    }

    @Override
    public void setDeleteFormVisiblePropertyIds(Object... deleteFormVisiblePropertyIds) {
        this.deleteFormVisiblePropertyIds = deleteFormVisiblePropertyIds;
    }

    @Override
    public void setEditFormDisabledPropertyIds(Object... editFormDisabledPropertyIds) {
        this.editFormDisabledPropertyIds = editFormDisabledPropertyIds;
    }

    @Override
    public void setNewFormFieldCaptions(String... newFormFieldCaptions) {
        this.newFormFieldCaptions = newFormFieldCaptions;
    }

    @Override
    public void setEditFormFieldCaptions(String... editFormFieldCaptions) {
        this.editFormFieldCaptions = editFormFieldCaptions;
    }

    @Override
    public void setDeleteFormFieldCaptions(String... deleteFormFieldCaptions) {
        this.deleteFormFieldCaptions = deleteFormFieldCaptions;
    }

    @Override
    public CrudLayout getMainLayout() {
        return mainLayout;
    }

    @Override
    public void setCrudFormBuilder(CrudFormBuilder<T> crudFormBuilder) {
        this.crudFormBuilder = crudFormBuilder;
    }

    @Override
    public void setOperations(Consumer<T> add, Consumer<T> update, Consumer<T> delete) {
        setAddOperation(add);
        setUpdateOperation(update);
        setDeleteOperation(delete);
    }

    @Override
    public void setAddOperation(Consumer<T> add) {
        this.add = add;
    }

    @Override
    public void setUpdateOperation(Consumer<T> update) {
        this.update = update;
    }

    @Override
    public void setDeleteOperation(Consumer<T> delete) {
        this.delete = delete;
    }

    @Override
    public void setCrudListener(CrudListener<T> crudListener) {
        setAddOperation(t -> crudListener.add(t));
        setUpdateOperation(t -> crudListener.update(t));
        setDeleteOperation(t -> crudListener.delete(t));
    }

}
