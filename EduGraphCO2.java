public class EduGraphCO2 {

    // ---------------- STUDENT NODE ---------------- //

    static class Student {

        int studentId;
        String studentName;
        String courseName;
        int score;

        Student left, right;

        Student(int studentId,
                String studentName,
                String courseName,
                int score) {

            this.studentId = studentId;
            this.studentName = studentName;
            this.courseName = courseName;
            this.score = score;

            left = right = null;
        }
    }

    // ---------------- B+ TREE SIMULATION USING BST ---------------- //

    static class BPlusTree {

        Student insert(Student root,
                       int studentId,
                       String studentName,
                       String courseName,
                       int score) {

            if (root == null) {

                return new Student(studentId,
                        studentName,
                        courseName,
                        score);
            }

            if (studentId < root.studentId) {

                root.left = insert(root.left,
                        studentId,
                        studentName,
                        courseName,
                        score);

            } else {

                root.right = insert(root.right,
                        studentId,
                        studentName,
                        courseName,
                        score);
            }

            return root;
        }

        void displayRecords(Student root) {

            if (root != null) {

                displayRecords(root.left);

                System.out.println(
                        "Student ID: " + root.studentId);

                System.out.println(
                        "Student Name: " + root.studentName);

                System.out.println(
                        "Course Name: " + root.courseName);

                System.out.println(
                        "Score: " + root.score);

                System.out.println();

                displayRecords(root.right);
            }
        }

        Student search(Student root, int studentId) {

            if (root == null ||
                    root.studentId == studentId) {

                return root;
            }

            if (studentId < root.studentId) {

                return search(root.left,
                        studentId);
            }

            return search(root.right,
                    studentId);
        }
    }

    // ---------------- SEGMENT TREE ---------------- //

    static class SegmentTree {

        int[] tree;
        int n;

        SegmentTree(int[] scores) {

            n = scores.length;

            tree = new int[4 * n];

            buildTree(scores,
                    0,
                    0,
                    n - 1);
        }

        void buildTree(int[] scores,
                       int node,
                       int start,
                       int end) {

            if (start == end) {

                tree[node] = scores[start];

            } else {

                int mid =
                        (start + end) / 2;

                buildTree(scores,
                        2 * node + 1,
                        start,
                        mid);

                buildTree(scores,
                        2 * node + 2,
                        mid + 1,
                        end);

                tree[node] =
                        tree[2 * node + 1] +
                        tree[2 * node + 2];
            }
        }

        int rangeQuery(int node,
                       int start,
                       int end,
                       int l,
                       int r) {

            if (r < start ||
                    end < l) {

                return 0;
            }

            if (l <= start &&
                    end <= r) {

                return tree[node];
            }

            int mid =
                    (start + end) / 2;

            int leftSum =
                    rangeQuery(2 * node + 1,
                            start,
                            mid,
                            l,
                            r);

            int rightSum =
                    rangeQuery(2 * node + 2,
                            mid + 1,
                            end,
                            l,
                            r);

            return leftSum + rightSum;
        }

        void updateScore(int node,
                         int start,
                         int end,
                         int index,
                         int value) {

            if (start == end) {

                tree[node] = value;

            } else {

                int mid =
                        (start + end) / 2;

                if (index <= mid) {

                    updateScore(2 * node + 1,
                            start,
                            mid,
                            index,
                            value);

                } else {

                    updateScore(2 * node + 2,
                            mid + 1,
                            end,
                            index,
                            value);
                }

                tree[node] =
                        tree[2 * node + 1] +
                        tree[2 * node + 2];
            }
        }
    }

    // ---------------- MAIN METHOD ---------------- //

    public static void main(String[] args) {

        BPlusTree tree =
                new BPlusTree();

        Student root = null;

        // Insert Student Records

        root = tree.insert(root,
                101,
                "Tejasri",
                "Data Structures",
                85);

        root = tree.insert(root,
                103,
                "Rahul",
                "Machine Learning",
                92);

        root = tree.insert(root,
                105,
                "Sneha",
                "Python Programming",
                78);

        root = tree.insert(root,
                110,
                "Kiran",
                "DBMS",
                88);

        // Display Records

        System.out.println(
                "Student Records in Sorted Order:");

        tree.displayRecords(root);

        // Search Record

        System.out.println(
                "Searching for Student ID 103");

        Student found =
                tree.search(root, 103);

        if (found != null) {

            System.out.println(
                    "Student Found:");

            System.out.println(
                    found.studentName +
                    " scored " +
                    found.score);
        }

        // Segment Tree Operations

        int[] scores =
                {85, 92, 78, 88};

        SegmentTree segTree =
                new SegmentTree(scores);

        // Range Query

        int total =
                segTree.rangeQuery(0,
                        0,
                        scores.length - 1,
                        1,
                        3);

        System.out.println(
                "\nTotal Score from Index 1 to 3: "
                        + total);

        // Update Score

        System.out.println(
                "\nUpdating Score at Index 2");

        segTree.updateScore(0,
                0,
                scores.length - 1,
                2,
                95);

        int updatedTotal =
                segTree.rangeQuery(0,
                        0,
                        scores.length - 1,
                        1,
                        3);

        System.out.println(
                "Updated Total Score: "
                        + updatedTotal);
    }
}