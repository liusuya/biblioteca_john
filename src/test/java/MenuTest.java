import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MenuTest {
        PrintStream printStream;
        Menu menu;
        Biblioteca biblioteca;
        BufferedReader bufferedReader;

    @Before
    public void setUp() throws Exception {
        printStream = mock(PrintStream.class);
        bufferedReader = mock(BufferedReader.class);
        biblioteca = mock(Biblioteca.class);
        menu = new Menu(printStream, bufferedReader, biblioteca);
    }

    @Test
    public void shouldDisplayAListOfOptionsWhenStarted() {
        menu.displayOptionList();
        verify(printStream).println("1. List books");
    }

    //Is this a trivial test?
    @Test
    public void shouldReadInputWhenPrompted() throws IOException {
        menu.getInput();
        verify(bufferedReader).readLine();
    }

    @Test
    public void shouldListBooksWhen1IsInput() throws IOException {
        when(bufferedReader.readLine()).thenReturn("1");
        menu.checkInput();
        verify(biblioteca).listBooks();
    }

    @Test
    public void shouldPrintErrorMessageWhen1NotEntered() throws Exception {
        when(bufferedReader.readLine()).thenReturn("0", "1");
        menu.checkInput();
        verify(printStream).println("Select a valid option!");
    }

    @Test
    public void shouldAskForAnotherInputIfInvalidInput() throws IOException {
        when(bufferedReader.readLine()).thenReturn("%","1");
        menu.checkInput();
        verify(biblioteca).listBooks();

    }

    @Test
    public void shouldQuitWhenQIsPressed() throws IOException {
        when(bufferedReader.readLine()).thenReturn("q");
        menu.checkInput();
        verify(printStream).println("Goodbye!");
    }
}