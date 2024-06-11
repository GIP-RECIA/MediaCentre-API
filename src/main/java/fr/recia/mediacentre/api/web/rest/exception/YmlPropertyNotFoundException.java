package fr.recia.mediacentre.api.web.rest.exception;

public class YmlPropertyNotFoundException extends Throwable {

    public YmlPropertyNotFoundException(String message){
        super(message);
    }

    public YmlPropertyNotFoundException(){
        super();
    }
}
