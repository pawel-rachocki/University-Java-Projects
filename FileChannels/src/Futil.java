import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class Futil {
    public static void processDir(String dirName, String resultFileName) {
        Path resultFile = Paths.get(resultFileName);

        // Clear (or create) the result file
        try (FileChannel resultChannel = FileChannel.open(resultFile, StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING)) {
            // Recursively visit all files in the directory tree
            Files.walkFileTree(Paths.get(dirName), new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    // Check if the file is a regular text file
                    if (Files.isRegularFile(file) && file.toString().endsWith(".txt")) {
                        try (FileChannel fileChannel = FileChannel.open(file, StandardOpenOption.READ)) {
                            // Prepare charset decoder and encoder for Cp1250 to UTF-8 conversion
                            CharsetDecoder decoder = Charset.forName("Cp1250").newDecoder();
                            CharsetEncoder encoder = Charset.forName("UTF-8").newEncoder();
                            decoder.onMalformedInput(CodingErrorAction.REPLACE);
                            decoder.onUnmappableCharacter(CodingErrorAction.REPLACE);
                            encoder.onMalformedInput(CodingErrorAction.REPLACE);
                            encoder.onUnmappableCharacter(CodingErrorAction.REPLACE);
                            ByteBuffer buffer = ByteBuffer.allocate(1024);
                            // Read from source file and write to the result file with encoding conversion
                            while (fileChannel.read(buffer) > 0) {
                                buffer.flip();
                                resultChannel.write(encoder.encode(decoder.decode(buffer)));
                                buffer.clear();
                            }
                        }
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
