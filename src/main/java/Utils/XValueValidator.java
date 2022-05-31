package Utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@ManagedBean
@SessionScoped
@Getter
@Setter
@FacesValidator("xValidator")
public class XValueValidator implements Validator {
    private double fromVal = -3;
    private double toVal = 3;

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
        double x = (double) o;
        if (x <= fromVal || x >= toVal){
            FacesMessage msg = new FacesMessage(String.format("X должен быть от %d до %d", (int)fromVal, (int)toVal));
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }
}
