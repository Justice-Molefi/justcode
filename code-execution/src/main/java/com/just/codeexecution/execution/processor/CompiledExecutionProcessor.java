package com.just.codeexecution.execution.processor;

import com.just.codeexecution.websocket.WebsocketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public abstract class CompiledExecutionProcessor implements CodeExecutor {
    private static final Logger log = LoggerFactory.getLogger(CompiledExecutionProcessor.class);
    private final WebsocketService websocketService;
    protected File baseDir;
    protected String code;
    protected String uuid;

    protected CompiledExecutionProcessor(WebsocketService websocketService) {
        this.websocketService = websocketService;
    }

    @Override
    public final void process(String code, String id){
        try {
            initializePayLoad(code, id);
            baseDir = createBaseDirectory();
            generateFile();
            int exitCode = compile();

            if(exitCode == 0){
                websocketService.sendOutput(id,">> Compilation successful");
                websocketService.sendOutput(id,">> Executing code...");
                run();
                websocketService.sendOutput(id, ">> Code Execution Complete");

            }else
                System.out.println(">> Execution skipped due to compilation failure.");

            cleanup(baseDir);
        }catch (Exception e){
            log.error(e.getMessage());
            websocketService.sendOutput(uuid, ">> Something went wrong, Please try again later");
        }
    }

    protected abstract void generateFile() throws FileNotFoundException;
    protected abstract int compile() throws IOException, InterruptedException;
    protected abstract void run() throws IOException, InterruptedException;

    private File createBaseDirectory(){
        File dir = new File("code-files/" + "temp-"+ uuid);

        if(dir.mkdirs())
            log.info("Base Directory Created");

        return dir;
    }

    //clean up everything inside "code-files" directory recursively
    private boolean cleanup(File file){
        if(file.isDirectory()){
            File[] contents = file.listFiles();
            if(contents != null){
                for(File f : contents){
                    if(!cleanup(f)) return false;
                }
            }
        }
        return file.delete();
    }

    private void initializePayLoad(String code, String uuid){
        this.uuid = uuid;
        this.code = code;
    }
    protected void readStream(InputStream stream, String id) throws IOException {
        log.info("Reading output stream");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                websocketService.sendOutput(id, line);
            }
        }
    }
}
