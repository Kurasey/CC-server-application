package me.t.kaurami.web.exceptionhandler;

import me.t.kaurami.service.bookReader.NotValidWorkbookException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RequestMapping("/error")
public class MVCExceptionHandler{

    @ExceptionHandler(NotValidWorkbookException.class)
    public String notValidWorkbookExceptionHandler(NotValidWorkbookException e, Model model) {
        model.addAttribute("exception", e);
        return "error";
    }
    @ExceptionHandler({Exception.class})
    public String otherExceptionHandler(Exception e, Model model) {
        model.addAttribute("exception", e);
        for (int i = 0; i<30; i++){
            e.printStackTrace();
        }
        System.exit(0);
        return "error";
    }

    @GetMapping(params = "reset")
    public String completeSession(SessionStatus status) {
        status.setComplete();
        return "redirect:/";
    }



/*    @ExceptionHandler(value
            = { IllegalArgumentException.class, IllegalStateException.class, NullPointerException.class })
    protected ResponseEntity<Object> handleConflict(
            RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "This should be application specific";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }*/

}
