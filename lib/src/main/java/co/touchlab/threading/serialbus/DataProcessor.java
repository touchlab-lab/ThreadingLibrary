package co.touchlab.threading.serialbus;

import android.util.Log;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Background processing 'bus'.  There are extra methods here for querying the bus.  This needs to be improved quite a bit, but I'd like to have some level of queue management.
 *
 * Created by kgalligan on 5/28/14.
 */
public class DataProcessor
{
    public static final String TAG = DataProcessor.class.getSimpleName();
    private static List<Runnable> commandList = new LinkedList<Runnable>();
    static AtomicBoolean running = new AtomicBoolean(false);

    public static synchronized void runProcess(Runnable r)
    {
        Log.i(TAG, "runCommand: " + r.getClass().getSimpleName());
        commandList.add(r);
        startCommands();
    }

    private static synchronized void removeCommand(Runnable command)
    {
        Log.d(DataProcessor.class.getSimpleName(), "removeCommand: " + command.toString());
        commandList.remove(command);
    }

    //Mostly complete.  Still possible to have something come in at the last minute
    private static void startCommands()
    {
        if (running.compareAndSet(false, true))
        {
            new Thread()
            {
                @Override
                public void run()
                {
                    try
                    {
                        while (!isCommandListEmpty())
                        {
                            Runnable top = grabTopCommand();
                            try
                            {
                                top.run();
                            }
                            finally
                            {
                                removeCommand(top);
                            }
                        }
                    }
                    finally
                    {
                        running.set(false);
                    }
                }
            }.start();
        }
    }

    private static synchronized boolean isCommandListEmpty()
    {
        return commandList.isEmpty();
    }

    private static synchronized Runnable grabTopCommand()
    {
        return commandList.get(0);
    }

    public static synchronized void queryCommands(Query query)
    {
        query.runQuery(commandList);
    }

    private static class SingleInstanceQuery implements Query
    {
        Class commandType;
        boolean anyFound;

        private SingleInstanceQuery(Class commandType)
        {
            this.commandType = commandType;
        }

        @Override
        public void runQuery(List<Runnable> commandList)
        {
            for (Runnable command : commandList)
            {
                if (command.getClass().getSimpleName().equals(commandType.getSimpleName()))
                {
                    anyFound = true;
                    break;
                }
            }
        }
    }

    public static boolean anyInstances(Class commandType)
    {
        SingleInstanceQuery query = new SingleInstanceQuery(commandType);
        queryCommands(query);
        Log.d(DataProcessor.class.getSimpleName(), "anyInstances[" + commandType.getSimpleName() + "]: " + query.anyFound);
        return query.anyFound;
    }

    public interface Query
    {
        void runQuery(List<Runnable> commandList);
    }
}
