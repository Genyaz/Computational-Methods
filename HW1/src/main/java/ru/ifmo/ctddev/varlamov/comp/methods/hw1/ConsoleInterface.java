package ru.ifmo.ctddev.varlamov.comp.methods.hw1;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConsoleInterface {
    public interface ConsoleCallback {
        void call(PrintStream out);
    }

    private final Map<String, ConsoleCallback> calls = new HashMap<>();
    private final BufferedReader in;
    private final PrintStream out;

    public ConsoleInterface(InputStream in, PrintStream out) {
        this.in = new BufferedReader(new InputStreamReader(in));
        this.out = out;
    }

    public void addCall(String key, ConsoleCallback callback) {
        calls.put(key, callback);
    }

    public void start() throws IOException {
        String s;
        Pattern set = Pattern.compile("set (?<key>.*) (?<value>.*)");
        Pattern get = Pattern.compile("get (?<key>.*)");
        Pattern call = Pattern.compile("call (?<key>.*)");
        Pattern getAll = Pattern.compile("getall");
        StateSingleton state = StateSingleton.getInstance();
        while ((s = in.readLine().toLowerCase()) != null) {
            Matcher m;
            if ((m = set.matcher(s)).matches()) {
                state.setValue(m.group("key"), m.group("value"));
            } else if ((m = get.matcher(s)).matches()) {
                out.println(state.getValue(m.group("key")));
            } else if ((m = getAll.matcher(s)).matches()) {
                for (String key : state.getAllKeys()) {
                    out.println(key);
                }
            } else if ((m = call.matcher(s)).matches()) {
                ConsoleCallback callback = calls.get(m.group("key"));
                if (callback != null) callback.call(out);
            }
        }
    }
}
