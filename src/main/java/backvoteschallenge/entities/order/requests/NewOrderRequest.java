package backvoteschallenge.entities.order.requests;

import backvoteschallenge.entities.order.entity.Order;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

public class NewOrderRequest {

    @ApiModelProperty(value = "Tema da Pauta", position = 1, required = true)
    @NotBlank
    private String theme;

    @ApiModelProperty(value = "Nome do Criador da Pauta", position = 2, required = true)
    @NotBlank
    private String editor;

    public NewOrderRequest(String theme, String editor) {
        this.theme = theme;
        this.editor = editor;
    }

    public Order toModel() {
        return new Order(this.theme, this.editor);
    }

    public NewOrderRequest() {
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

}
