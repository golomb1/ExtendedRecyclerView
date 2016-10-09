package com.libs.golomb;

import com.squareup.javapoet.MethodSpec;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

/**
 * Created by tomer on 09/10/2016.
 */
@SupportedAnnotationTypes({"com.libs.golomb.ItemViewTypeAnnotation"})
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class ItemViewTypeProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        // for each javax.lang.model.element.Element annotated with the CustomAnnotation
        for (Element element : roundEnv.getElementsAnnotatedWith(ItemViewTypeAnnotation.class)) {
            //String objectType = element.getSimpleName().toString();
           processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING, "CCCC " + element.getEnclosingElement().getSimpleName());

        }
        return true;
    }
}
