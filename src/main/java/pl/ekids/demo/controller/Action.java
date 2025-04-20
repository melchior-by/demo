package pl.ekids.demo.controller;

public enum Action {
    LIST, VIEW, EDIT, DELETE, CREATE, UPDATE;

    public static Action from(String value) {
        try {
            return Action.valueOf(value.toUpperCase());
        } catch (Exception e) {
            return null;
        }
    }
}
