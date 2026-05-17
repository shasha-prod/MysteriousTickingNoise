public interface Observable {
    void register(TickListener listener);
    void notifyListeners(int tick);
}
