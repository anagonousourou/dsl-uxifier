package uxifier.models

class WebApplication {

    String name;

    WebApplication(String name) {
        this.name = name
    }


    @Override
    public String toString() {
        return "<h1>${name}</h1>"
    }
}
