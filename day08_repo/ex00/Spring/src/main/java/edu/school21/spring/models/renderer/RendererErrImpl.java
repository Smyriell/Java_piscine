package edu.school21.spring.models.renderer;

import edu.school21.spring.models.preprocessor.PreProcessor;

public class RendererErrImpl implements Renderer {
    private final PreProcessor preProcessor;

    public RendererErrImpl(PreProcessor preProcessor) {
        this.preProcessor = preProcessor;
    }

    @Override
    public void render(String text) {
        System.err.println(preProcessor.process(text));
    }
}
