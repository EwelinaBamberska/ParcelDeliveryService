package commands;

public interface ICommandResult<T,Y> {
    T GetResult();
    Y Execute();
}
