package edu.school21.spring.app;

import edu.school21.spring.models.preprocessor.PreProcessor;
import edu.school21.spring.models.preprocessor.PreProcessorToLower;
import edu.school21.spring.models.preprocessor.PreProcessorToUpperImpl;
import edu.school21.spring.models.printer.Printer;
import edu.school21.spring.models.printer.PrinterWithDateTimeImpl;
import edu.school21.spring.models.printer.PrinterWithPrefixImpl;
import edu.school21.spring.models.renderer.Renderer;
import edu.school21.spring.models.renderer.RendererErrImpl;
import edu.school21.spring.models.renderer.RendererStandardImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void manualWay() {
        PreProcessor preProcessor = new PreProcessorToUpperImpl();
        Renderer renderer = new RendererErrImpl(preProcessor);
        PrinterWithPrefixImpl printer = new PrinterWithPrefixImpl(renderer);

        printer.setPrefix("Prefix");
        printer.print("Hello!");

        PreProcessor preProcessor2 = new PreProcessorToLower();
        Renderer renderer2 = new RendererErrImpl(preProcessor2);
        PrinterWithPrefixImpl printer2 = new PrinterWithPrefixImpl(renderer2);
        printer2.setPrefix("Prefix");
        printer2.print("Hello!");

        PreProcessor preProcessor3 = new PreProcessorToLower();
        Renderer renderer3 = new RendererErrImpl(preProcessor3);
        Printer printer3 = new PrinterWithDateTimeImpl(renderer3);
        printer3.print("Hello!");

        PreProcessor preProcessor4 = new PreProcessorToUpperImpl();
        Renderer renderer4 = new RendererStandardImpl(preProcessor4);
        PrinterWithPrefixImpl printer4 = new PrinterWithPrefixImpl(renderer4);
        printer4.setPrefix("Prefix");
        printer4.print("Hello!");

        PreProcessor preProcessor5 = new PreProcessorToLower();
        Renderer renderer5 = new RendererStandardImpl(preProcessor5);
        PrinterWithPrefixImpl printer5 = new PrinterWithPrefixImpl(renderer5);
        printer5.setPrefix("Prefix");
        printer5.print("Hello!");

        PreProcessor preProcessor6 = new PreProcessorToUpperImpl();
        Renderer renderer6 = new RendererStandardImpl(preProcessor6);
        Printer printer6 = new PrinterWithDateTimeImpl(renderer6);
        printer6.print("Hello!");
    }

    public static void contextWay() {
        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");

        Printer printer = context.getBean("printerWithPrefix_errUp", Printer.class);
        printer.print("Hello!");

        Printer printer2 = context.getBean("printerWithPrefix_errLow", Printer.class);
        printer2.print("Hello!");

        Printer printer5 = context.getBean("printerWithDateTime_errLow", Printer.class);
        printer5.print("Hello!");

        Printer printer3 = context.getBean("printerWithPrefix_standUp", Printer.class);
        printer3.print("Hello!");

        Printer printer4 = context.getBean("printerWithPrefix_standLow", Printer.class);
        printer4.print("Hello!");

        Printer printer6 = context.getBean("printerWithDateTime_standUp", Printer.class);
        printer6.print("Hello!");
    }

    public static void main(String[] args) {
        System.out.println("\n___STANDARD WAY___");
        System.out.flush();
        manualWay();
        System.out.flush();
        System.out.println("\n___CONTEXT WAY___");
        contextWay();
    }
}
