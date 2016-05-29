# Practicum OO – lente 2016 Opgave 14
#### Goals
This exercise consists of three programs with threads. Use the 6 steps approach to designing concurrent
programs outlined in the lectures and available on BlackBoard. After this exercise you should
be able to:
+ synchronize threads;
+ stop threads;
+ use the Worker interface and the class Task.

## Simulating trains and taxis transporting people to and from a station

### Introduction

In this exercise we will simulate the process of arrivals of a train with persons and the transportation
of them to a certain location by taxis. We start with a sequential solution which must be
changed to a concurrent version in the assignment.
We will use simple classes so the focus is placed on the process of transporting itself. A train
transports a number of persons (between 60 and 90) to a station. These persons must be transported
to a holiday location at some distance. Four taxis are available for the transport: two with
a capacity of four persons and two with a capacity of seven persons.
There are 10 arrivals of the train and the simulation stops when all the passengers have been
transported.

Method step handles the actual simulation: if there are passengers waiting at the station, the next
taxi transports a number of persons according to its capacity or less, depending on the available
number of persons at the station. If there are no passengers left, the next train can bring new
passengers. The last train closes the station

Method takePassengers() of class Taxi tries to take as many passengers as possible (limited by
its capacity).

### Assignment1:
Use the classes of the sequential version as the basis for a new concurrent version.


## 2: Stopping Threads
Although each Thread has a method stop, this is not the way to stop a thread. See the lecture
and https://docs.oracle.com/javase/8/docs/technotes/guides/concurrency/threadPrimitiveDeprecation.html for more details. The proper way to stop a thread in a safe way is that the run method checks every now and then whether it is supposed to stop. When this is the case, it should stop in such a way that all other threads interacting with this thread are not harmed. A simple way to indicate that a thread has to stop is by setting a boolean attribute of the task.

### Assignment2: 
Make a JavaFX program with an interface as depicted in Figure 2. When the start button is pressed the JavaFX program starts computing the Ackermann function with the arguments specified in the interface.



The Ackermann function is defined as:


	Ackermann(m,n) =
			n+1										if m==0
			Ackermann(m-1, 1)						if m > 0 and n = 0
			Ackermann(m-1, Ackermann(m, n-1))		if m > 0 and n > 0
			


Since the computation of such an Ackermann number can take very much time, you should use a separate thread for the computation in order to keep your program responsive. Only the JavaFX thread is allowed to change the nodes in the interface. An attempt of any other thread to change this will cause an exception. Hence, the task computing the Ackermann number cannot set the result. The thread executing the event handler cannot wait until the computation is finished, this would make your program unresponsive. The proper solution is that the computation thread asks the JavaFX thread to update the result field. JavaFX provides Platform.runLater(Runnable runnable) for this purpose. It will run the specified Runnable on the JavaFX Application Thread at some unspecified time in the future. Pressing the stop button should stop the running computation of the Ackermann number. Ensure that there appears a message in the interface indicating that the computation is interrupted. The computation of an Ackermann number can consume huge amounts of stack space due to very deep nested recursive calls. When the stack space is exhausted the thread will throw an exception. In order to handle such an exception, you use the method setUncaughtExceptionHandler(Thread.UncaughtExceptionHandler eh) of the thread. The interface UncaughtExceptionHandler just contains the method void uncaughtException(Thread t, Throwable e) ;


## 3: Using Worker and Task
Since it is very common that one wants to stop a thread or to observe its progress, Java provides
a standard solution in the form of the Worker interface and the classes Task and Service, both
implementing Worker.
Most Tasks require some parameters in order to do useful work. Because Tasks operate on a
background thread, care must be taken that external method calls do not modify the state during
the computation. There are two techniques most useful for doing this: final variables and passing
variables to a Task in the constructor.

#### The Interface Worker
The Worker interface defines an object that executes on a background thread. Each Worker
has read-only properties observable from the JavaFX Application thread: title (String), message
(String), running (Boolean), state (Enum Worker.State), totalWork (double), workDone (double),
progress (double), value (Object), and exception (Object). The enumeration type Worker.State
has states: READY, SCHEDULED, RUNNING, SUCCEEDED, CANCELLED, and FAILED. JavaFX
provides two abstract classes implementing this interface: Task<V> and Service<V>. Services
are intended for repeating workers, and are not considered here

#### The abstract Class Task<V>
The abstract Class Task<V> is meant for one-shot workers. The state of Task objects is defined
by the enumeration type Worker.State and can be depicted as in Figure 3.

The class Task<V> implements the Worker interface and adds many methods like:

protected abstract V call( ) throws Exception
public fin al boolean cancel( )
public boolean isCancelled( )
protected void updateTitle(String title)
protected void updateMessage(String message)
protected void updateProgress(long workDone, long totalWork)
protected void updateValue(V value)
protected void succeeded( )
protected void cancelled( )
V getValue( )
6
void setOnScheduled(EventHandler<WorkerStateEvent> value)
void setOnSucceeded(EventHandler<WorkerStateEvent> value)
void setOnCancelled(EventHandler<WorkerStateEvent> value)
void setOnFailed(EventHandler<WorkerStateEvent> value)

A Task can be executed by a thread similar to a runnable. The advantage of a Task is that it can
be cancelled and that its progress can be determined. Note that a Task has an abstract method
V call() throws Exception instead of the void run() of the Runnable interface. Hence, you
should define call instead of run.


### Assignment3:
Use the class Task and its native methods cancel() and isCancelled() to implement the interruptible
Ackermann computation from the previous section. Use setOnSucceeded with an
appropriate handler, or override succeeded() to update the GUI.



## Deadline
####Sunday May 29, 23:59.
