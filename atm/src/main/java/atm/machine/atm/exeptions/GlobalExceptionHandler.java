package atm.machine.atm.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<String> handleMissingParameterError(
            MissingServletRequestParameterException e,
            WebRequest request
    ){

        if(request.getDescription(false).contains("/balance")){
            return new ResponseEntity<String>("accountNumber and token parameters are required! accountNumber must be a Number, Int or Long, whereas token must be a String.", HttpStatus.BAD_REQUEST);
        }else if(request.getDescription(false).contains("/session")){
            return new ResponseEntity<String>("accountNumber and pin parameters are required! accountNumber must be a Number Int or Long, whereas pin must be a String.", HttpStatus.BAD_REQUEST);
        }else if(request.getDescription(false).contains("/withdraw")){
            return new ResponseEntity<String>("accountNumber, token and value parameters are required! accountNumber must be a Number Int or Long, whereas token must be a String and value an Integer.", HttpStatus.BAD_REQUEST);
        }else{
            return new ResponseEntity<String>("Missing Parameters", HttpStatus.BAD_REQUEST);
        }

    }

    @ExceptionHandler(NoEnoughCashError.class)
    public ResponseEntity<String> handleNoEnoughCashError(NoEnoughCashError e){
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ValueWithInvalidMultipleError.class)
    public ResponseEntity<String> handleValueWithInvalidMultipleError(ValueWithInvalidMultipleError e){
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidTokenError.class)
    public ResponseEntity<String> handleInvalidTokenError(InvalidTokenError e){
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExpiredTokenError.class)
    public ResponseEntity<String> handleExpiredTokenError(ExpiredTokenError e){
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ATMOutOfBankNotesError.class)
    public ResponseEntity<String> handleATMBankNotesError(ATMOutOfBankNotesError e){
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NonExistentAccountError.class)
    public ResponseEntity<String> handleNonExistentAccountError(NonExistentAccountError e){
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidPinError.class)
    public ResponseEntity<String> handleInvalidPinError(InvalidPinError e){
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String>handleGenericError(Exception e, WebRequest request){
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
