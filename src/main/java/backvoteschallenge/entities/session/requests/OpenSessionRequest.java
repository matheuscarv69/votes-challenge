package backvoteschallenge.entities.session.requests;

import backvoteschallenge.entities.order.entity.Order;
import backvoteschallenge.entities.session.entity.Session.Session;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

public class OpenSessionRequest {

    @ApiModelProperty(value = "Id da Pauta", position = 1, required = true)
    @NotNull
    @Positive
    private Long orderId;

    @ApiModelProperty(value = "Duração da Pauta em minutos", position = 2, required = true)
    @Positive
    private Integer duration;

    public OpenSessionRequest(Long orderId, Integer duration) {
        this.orderId = orderId;
        this.duration = duration;
    }

    public Session toModel(Order order) {
        LocalDateTime finishSession = duration == null ?
                LocalDateTime.now().plusMinutes(1L) : LocalDateTime.now().plusMinutes(this.duration);

        return new Session(order, finishSession);
    }

    public OpenSessionRequest() {
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }
}
