import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
public class SortedListGUI extends JFrame
{
    SortedList sortedList = new SortedList();
    JPanel mainPnl;
    JPanel inputPnl;
    JPanel buttonPnl;
    JTextField addTF;
    JTextField searchTF;
    JTextArea logTA;
    JScrollPane scroller;
    JButton addBtn;
    JButton searchBtn;
    JButton clearBtn;
    JButton quitBtn;
    Font labelFont = new Font("SansSerif", Font.PLAIN, 15);
    Font textFont = new Font("Monospaced", Font.PLAIN, 13);
    Font buttonFont = new Font("SansSerif", Font.BOLD, 16);
    public SortedListGUI()
    {
        mainPnl = new JPanel(new BorderLayout());
        createInputPanel();
        mainPnl.add(inputPnl, BorderLayout.NORTH);
        logTA = new JTextArea();
        logTA.setFont(textFont);
        logTA.setEditable(false);
        scroller = new JScrollPane(logTA);
        scroller.setBorder(new TitledBorder("Sorted List Operations"));
        mainPnl.add(scroller, BorderLayout.CENTER);
        createButtonPanel();
        mainPnl.add(buttonPnl, BorderLayout.SOUTH);
        add(mainPnl);
    }
    private void createInputPanel()
    {
        inputPnl = new JPanel(new GridLayout(2, 1, 5, 5));
        inputPnl.setBorder(new TitledBorder("Enter Data"));
        JPanel addRow = new JPanel(new BorderLayout(10, 5));
        JLabel addLbl = new JLabel("Add string:     ");
        addLbl.setFont(labelFont);
        addTF = new JTextField();
        addTF.setFont(labelFont);
        addTF.addActionListener(e -> addElement());
        addRow.add(addLbl, BorderLayout.WEST);
        addRow.add(addTF, BorderLayout.CENTER);
        JPanel searchRow = new JPanel(new BorderLayout(10, 5));
        JLabel searchLbl = new JLabel("Search for:      ");
        searchLbl.setFont(labelFont);
        searchTF = new JTextField();
        searchTF.setFont(labelFont);
        searchTF.addActionListener(e -> search());
        searchRow.add(searchLbl, BorderLayout.WEST);
        searchRow.add(searchTF, BorderLayout.CENTER);
        inputPnl.add(addRow);
        inputPnl.add(searchRow);
    }
    private void createButtonPanel()
    {
        buttonPnl = new JPanel(new GridLayout(1, 4));
        addBtn = new JButton("Add");
        searchBtn = new JButton("Search");
        clearBtn = new JButton("Clear List");
        quitBtn = new JButton("Quit");
        for (JButton btn : new JButton[]{addBtn, searchBtn, clearBtn, quitBtn})
        {
            btn.setFont(buttonFont);
            buttonPnl.add(btn);
        }
        addBtn.addActionListener(e -> addElement());
        searchBtn.addActionListener(e -> search());
        clearBtn.addActionListener(e ->
        {
            sortedList.clear();
            logTA.setText("");
            log("List cleared.");
            addTF.requestFocus();
        });
        quitBtn.addActionListener(e -> System.exit(0));
    }
    private void addElement()
    {
        String value = addTF.getText().trim();
        if (value.isEmpty())
        {
            JOptionPane.showMessageDialog(this, "Enter a string to add.");
            return;
        }
        int index = sortedList.add(value);
        log("ADD \"" + value + "\" -> inserted at index " + index);
        log("    List: " + listString());
        addTF.setText("");
        addTF.requestFocus();
    }
    private void search()
    {
        String value = searchTF.getText().trim();
        if (value.isEmpty())
        {
            JOptionPane.showMessageDialog(this, "Enter a string to search for.");
            return;
        }
        int result = sortedList.binarySearch(value);
        if (result >= 0)
        {
            log("SEARCH \"" + value + "\" -> FOUND \"" + sortedList.get(result)
                    + "\" at index " + result);
        }
        else
        {
            int insertionPoint = -(result + 1);
            String where;
            if (sortedList.isEmpty())
            {
                where = "the list is empty";
            }
            else if (insertionPoint == 0)
            {
                where = "before \"" + sortedList.get(0) + "\"";
            }
            else if (insertionPoint == sortedList.size())
            {
                where = "after \"" + sortedList.get(sortedList.size() - 1) + "\"";
            }
            else
            {
                where = "between \"" + sortedList.get(insertionPoint - 1)
                        + "\" and \"" + sortedList.get(insertionPoint) + "\"";
            }
            log("SEARCH \"" + value + "\" -> NOT FOUND; it would be at index "
                    + insertionPoint + " (" + where + ")");
        }
        searchTF.setText("");
        searchTF.requestFocus();
    }
    private String listString()
    {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < sortedList.size(); i++)
        {
            if (i > 0)
            {
                sb.append(", ");
            }
            sb.append(i).append(":").append(sortedList.get(i));
        }
        return sb.append("]").toString();
    }
    private void log(String line)
    {
        logTA.append(line + System.lineSeparator());
        logTA.setCaretPosition(logTA.getDocument().getLength());
    }
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() ->
        {
            try
            {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            }
            catch (Exception ex)
            {
            }
            SortedListGUI frame = new SortedListGUI();
            frame.setTitle("Sorted List - Binary Search");
            frame.setSize(950, 550);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
