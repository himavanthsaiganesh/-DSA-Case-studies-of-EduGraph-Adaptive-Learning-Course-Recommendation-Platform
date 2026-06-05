public class EduGraphBSTAVL {

    // Node Class
    static class CourseNode {

        int courseId;
        String courseName;
        String instructorName;

        CourseNode left, right;
        int height;

        CourseNode(int courseId,
                   String courseName,
                   String instructorName) {

            this.courseId = courseId;
            this.courseName = courseName;
            this.instructorName = instructorName;

            height = 1;
        }
    }

    // AVL Tree Class
    static class AVLTree {

        int height(CourseNode node) {

            if (node == null)
                return 0;

            return node.height;
        }

        int getBalance(CourseNode node) {

            if (node == null)
                return 0;

            return height(node.left) - height(node.right);
        }

        CourseNode rotateRight(CourseNode y) {

            CourseNode x = y.left;
            CourseNode T2 = x.right;

            x.right = y;
            y.left = T2;

            y.height = Math.max(height(y.left),
                    height(y.right)) + 1;

            x.height = Math.max(height(x.left),
                    height(x.right)) + 1;

            return x;
        }

        CourseNode rotateLeft(CourseNode x) {

            CourseNode y = x.right;
            CourseNode T2 = y.left;

            y.left = x;
            x.right = T2;

            x.height = Math.max(height(x.left),
                    height(x.right)) + 1;

            y.height = Math.max(height(y.left),
                    height(y.right)) + 1;

            return y;
        }

        CourseNode insert(CourseNode node,
                          int courseId,
                          String courseName,
                          String instructorName) {

            if (node == null) {

                return new CourseNode(courseId,
                        courseName,
                        instructorName);
            }

            if (courseId < node.courseId) {

                node.left = insert(node.left,
                        courseId,
                        courseName,
                        instructorName);

            } else if (courseId > node.courseId) {

                node.right = insert(node.right,
                        courseId,
                        courseName,
                        instructorName);
            }

            else {
                return node;
            }

            node.height = 1 + Math.max(height(node.left),
                    height(node.right));

            int balance = getBalance(node);

            // Left Left
            if (balance > 1 &&
                    courseId < node.left.courseId)

                return rotateRight(node);

            // Right Right
            if (balance < -1 &&
                    courseId > node.right.courseId)

                return rotateLeft(node);

            // Left Right
            if (balance > 1 &&
                    courseId > node.left.courseId) {

                node.left = rotateLeft(node.left);

                return rotateRight(node);
            }

            // Right Left
            if (balance < -1 &&
                    courseId < node.right.courseId) {

                node.right = rotateRight(node.right);

                return rotateLeft(node);
            }

            return node;
        }

        void inorder(CourseNode root) {

            if (root != null) {

                inorder(root.left);

                System.out.println("Course ID: " +
                        root.courseId);

                System.out.println("Course Name: " +
                        root.courseName);

                System.out.println("Instructor Name: " +
                        root.instructorName);

                System.out.println();

                inorder(root.right);
            }
        }

        CourseNode search(CourseNode root,
                          int courseId) {

            if (root == null ||
                    root.courseId == courseId)

                return root;

            if (courseId < root.courseId)

                return search(root.left, courseId);

            return search(root.right, courseId);
        }

        CourseNode minValueNode(CourseNode node) {

            CourseNode current = node;

            while (current.left != null)
                current = current.left;

            return current;
        }

        CourseNode delete(CourseNode root,
                          int courseId) {

            if (root == null)
                return root;

            if (courseId < root.courseId) {

                root.left = delete(root.left,
                        courseId);

            } else if (courseId > root.courseId) {

                root.right = delete(root.right,
                        courseId);

            } else {

                if ((root.left == null) ||
                        (root.right == null)) {

                    CourseNode temp;

                    if (root.left != null)
                        temp = root.left;
                    else
                        temp = root.right;

                    if (temp == null) {

                        temp = root;
                        root = null;

                    } else {

                        root = temp;
                    }

                } else {

                    CourseNode temp =
                            minValueNode(root.right);

                    root.courseId = temp.courseId;
                    root.courseName = temp.courseName;
                    root.instructorName =
                            temp.instructorName;

                    root.right = delete(root.right,
                            temp.courseId);
                }
            }

            if (root == null)
                return root;

            root.height = Math.max(height(root.left),
                    height(root.right)) + 1;

            int balance = getBalance(root);

            // Left Left
            if (balance > 1 &&
                    getBalance(root.left) >= 0)

                return rotateRight(root);

            // Left Right
            if (balance > 1 &&
                    getBalance(root.left) < 0) {

                root.left =
                        rotateLeft(root.left);

                return rotateRight(root);
            }

            // Right Right
            if (balance < -1 &&
                    getBalance(root.right) <= 0)

                return rotateLeft(root);

            // Right Left
            if (balance < -1 &&
                    getBalance(root.right) > 0) {

                root.right =
                        rotateRight(root.right);

                return rotateLeft(root);
            }

            return root;
        }
    }

    // MAIN METHOD

    public static void main(String[] args) {

        AVLTree tree = new AVLTree();

        CourseNode root = null;

        // Insert Courses

        root = tree.insert(root,
                101,
                "Data Structures",
                "Prof. Seymour");

        root = tree.insert(root,
                103,
                "Python Programming",
                "Guido");

        root = tree.insert(root,
                105,
                "Java Programming",
                "James Gosling");

        root = tree.insert(root,
                110,
                "Machine Learning",
                "Andrew Ng");

        // Display Courses

        System.out.println(
                "Course Records in Sorted Order:");

        tree.inorder(root);

        // Search Course

        System.out.println(
                "Searching for Course ID 103");

        CourseNode found =
                tree.search(root, 103);

        if (found != null) {

            System.out.println("Course Found:");

            System.out.println(
                    found.courseName +
                    " by " +
                    found.instructorName);
        }

        // Delete Course

        System.out.println(
                "\nDeleting Course ID 105\n");

        root = tree.delete(root, 105);

        // Updated Records

        System.out.println(
                "Updated Course Records:");

        tree.inorder(root);
    }
}