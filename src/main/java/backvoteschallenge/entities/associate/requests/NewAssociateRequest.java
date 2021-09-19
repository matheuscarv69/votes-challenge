package backvoteschallenge.entities.associate.requests;

import backvoteschallenge.entities.associate.entity.associate.Associate;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotBlank;

public class NewAssociateRequest {

    @ApiModelProperty(value = "Nome do Associado", position = 1, required = true)
    @NotBlank
    private String name;

    @ApiModelProperty(value = "CPF", position = 2, required = true)
    @CPF
    @NotBlank
    private String document;

    public NewAssociateRequest(String name, String document) {
        this.name = name;
        this.document = document;
    }

    public Associate toModel() {
        return new Associate(this.name, this.document);
    }

    public NewAssociateRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }
}
