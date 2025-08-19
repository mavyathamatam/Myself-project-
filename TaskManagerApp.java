import java.util.Scanner;

abstract class AbstractTaskList {
    abstract void addTask(String name, int priority);

    abstract void showTasks();

    abstract void deleteTask(String name);

    abstract void markTaskCompleted(String name);

    abstract void showStatistics();
}

class TaskNode {
    String taskName;
    int priority;
    boolean completed; // New field to track completion status
    TaskNode next;
    TaskNode prev;

    TaskNode(String name, int priority) {
        this.taskName = name;
        this.priority = priority;
        this.completed = false; // Initially not completed
        this.next = null;
        this.prev = null;
    }
}

class DoublyTaskList extends AbstractTaskList {
    private TaskNode head;

    @Override
    void addTask(String name, int priority) {
        TaskNode newNode = new TaskNode(name, priority);
        if (head == null) {
            head = newNode;
        } else {
            TaskNode temp = head;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = newNode;
            newNode.prev = temp;
        }
        System.out.println("Task added successfully.");
    }

    @Override
    void showTasks() {
        if (head == null) {
            System.out.println("Task list is empty.");
            return;
        }
        TaskNode temp = head;
        System.out.println("Current Tasks:");
        while (temp != null) {
            String status = temp.completed ? "Completed" : "Pending";
            System.out.println("→ Task: \"" + temp.taskName + "\", Priority: " + temp.priority + ", Status: " + status);
            temp = temp.next;
        }
    }

    @Override
    void deleteTask(String name) {
        if (head == null) {
            System.out.println("No tasks to delete.");
            return;
        }

        TaskNode temp = head;
        while (temp != null) {
            if (temp.taskName.equalsIgnoreCase(name)) {
                if (temp.prev != null) {
                    temp.prev.next = temp.next;
                } else {
                    head = temp.next;
                }
                if (temp.next != null) {
                    temp.next.prev = temp.prev;
                }
                System.out.println("Task \"" + name + "\" deleted.");
                return;
            }
            temp = temp.next;
        }
        System.out.println("Task \"" + name + "\" not found.");
    }

    @Override
    void markTaskCompleted(String name) {
        TaskNode temp = head;
        while (temp != null) {
            if (temp.taskName.equalsIgnoreCase(name)) {
                if (!temp.completed) {
                    temp.completed = true;
                    System.out.println("Task \"" + name + "\" marked as completed.");
                } else {
                    System.out.println("Task \"" + name + "\" is already completed.");
                }
                return;
            }
            temp = temp.next;
        }
        System.out.println("Task \"" + name + "\" not found.");
    }

    @Override
    void showStatistics() {
        int total = 0;
        int completedCount = 0;
        TaskNode temp = head;

        while (temp != null) {
            total++;
            if (temp.completed) {
                completedCount++;
            }
            temp = temp.next;
        }

        int pendingCount = total - completedCount;

        System.out.println("=== Task Statistics ===");
        System.out.println("Total tasks: " + total);
        System.out.println("Completed tasks: " + completedCount);
        System.out.println("Pending tasks: " + pendingCount);
    }
}

public class TaskManagerApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AbstractTaskList taskList = new DoublyTaskList();
        int choice;

        do {
            System.out.println("\n=== TASK MANAGER MENU ===");
            System.out.println("1. Add Task");
            System.out.println("2. View Tasks");
            System.out.println("3. Delete Task");
            System.out.println("4. Mark Task as Completed");
            System.out.println("5. View Task Statistics");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            while (!sc.hasNextInt()) {
                System.out.print("Invalid input. Enter a number: ");
                sc.next();
            }

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter task name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter priority (1-5): ");
                    int priority = sc.nextInt();
                    sc.nextLine();
                    taskList.addTask(name, priority);
                    break;

                case 2:
                    taskList.showTasks();
                    break;

                case 3:
                    System.out.print("Enter task name to delete: ");
                    String deleteName = sc.nextLine();
                    taskList.deleteTask(deleteName);
                    break;

                case 4:
                    System.out.print("Enter task name to mark as completed: ");
                    String completeName = sc.nextLine();
                    taskList.markTaskCompleted(completeName);
                    break;

                case 5:
                    taskList.showStatistics();
                    break;

                case 6:
                    System.out.println("Exiting program. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        } while (choice != 6);

        sc.close();
    }
}
