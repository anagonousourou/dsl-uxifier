package generators

import models.WebApplication
import vue.project.models.VueProject

class ToVuejsCode implements  WebApplicationVisitor{

    int passNumber=1;

    VueProject vueProject=new VueProject();


    @Override
    void visit(WebApplication application) {
         this.vueProject.name=application.name;
    }
}
