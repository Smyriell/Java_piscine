package edu.school21.printer.logic;

import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.diogonunes.jcdp.color.api.Ansi;

@Parameters(separators = "=")
public class Args implements IStringConverter<Ansi.BColor> {

    @Parameter(names = "--white")
    private Ansi.BColor background;

    @Parameter(names = "--black")
    private Ansi.BColor sign;

    @Override
    public Ansi.BColor convert(String s) {
        switch (s) {
            case "40":
                return Ansi.BColor.BLACK;
            case "41":
                return Ansi.BColor.RED;
            case "42":
                return Ansi.BColor.GREEN;
            case "43":
                return Ansi.BColor.YELLOW;
            case "44":
                return Ansi.BColor.BLUE;
            case "45":
                return Ansi.BColor.MAGENTA;
            case "46":
                return Ansi.BColor.CYAN;
            case "47":
                return Ansi.BColor.WHITE;
            case "":
                return Ansi.BColor.NONE;
            default:
                throw new IllegalArgumentException("Invalid color name. Avaliable colors:\n " +
                        "BLACK\tRED\tGREEN\tYELLOW\tBLUE\tMAGENTA\tCYAN\tWHITE\tNONE");
        }
    }

    public Ansi.BColor getBackground() {
        return background;
    }

    public Ansi.BColor getSign() {
        return sign;
    }
}
