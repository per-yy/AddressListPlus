package ui;

import io.AddUpdate;
import io.ChangeUpdate;
import io.DeleteUpdate;
import io.SearchQuery;
import person.Person;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;

import java.util.Objects;

public class MainFrame extends JFrame implements ActionListener, TableModelListener {
    //菜单选项
    private JMenuItem Add;
    private JMenuItem Delete;
    //增删时的对话框
    private JDialog Dialog;
    //添加确认
    private JButton addEnsure;
    //删除确认
    private JButton deleteEnsure;
    //搜索确认
    private JButton searchEnsure;
    //刷新按钮
    private JButton update;
    //添加和修改联系人时的属性
    private JTextField name;
    private JTextField sex;
    private JTextField number;
    private JTextField address;
    //删、改、查联系人时的输入
    private JTextField Text;
    //删除和修改联系人时的选项组
    private JRadioButton option1;
    private JRadioButton option2;
    private ButtonGroup group;
    //搜索时的选项组
    private JComboBox<String> comboBox;
    //排序方式
    private JComboBox<String> orderBox;

    //页面显示表格model
    private DefaultTableModel model;
    //用于存在table中的数据
    private String[][] data;
    //存放table的面板
    private JScrollPane scrollPane;


    public MainFrame() {
        initFrame();
        initMenu();
        initFunction();
        scrollPane=new JScrollPane();//初始化面板
        initPanel();
        this.setVisible(true);
    }

    private void initFrame() {
        this.setTitle("通讯录");
        this.setSize(600, 400);

        //关闭拉伸
        this.setResizable(false);

        //居中
        this.setLocationRelativeTo(null);

        //关闭默认布局
        this.setLayout(null);
        //默认关闭方式
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initMenu() {
        //菜单条
        JMenuBar Bar = new JMenuBar();

        JMenu Function = new JMenu("功能");

        Add = new JMenuItem("添加联系人");
        Delete = new JMenuItem("删除联系人");

        Function.add(Add);
        Function.add(Delete);

        Bar.add(Function);

        Add.addActionListener(this);
        Delete.addActionListener(this);
        this.setJMenuBar(Bar);
    }

    private void initPanel() {
        this.getContentPane().removeAll();

        ArrayList<Person> per = null;

        initFunction();

        try {
            per = SearchQuery.queryAllByName();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"初始化错误！"+e.getMessage());
        }

        scrollPane = new JScrollPane(initTable(per));
        scrollPane.setBounds(0, 20, 586, 320);

        this.add(scrollPane);

        this.getContentPane().repaint();
    }

    private void updatePanel(ArrayList<Person> per) {
        //清除面板
        this.getContentPane().remove(scrollPane);

        scrollPane = new JScrollPane(initTable(per));
        scrollPane.setBounds(0, 20, 586, 320);

        this.add(scrollPane);
    }

    private JTable initTable(ArrayList<Person> per) {
        String[] colNames = {"姓名", "性别", "电话号码", "家庭住址"};
        data = new String[per.size()][4];
        for (int i = 0; i < per.size(); i++) {
            data[i][0] = per.get(i).getName();
            data[i][1] = per.get(i).getSex();
            data[i][2] = per.get(i).getPhoneNumber();
            data[i][3] = per.get(i).getAddress();
        }
        model=new DefaultTableModel(data, colNames);
        model.addTableModelListener(this);
        return new JTable(model);
    }

    private void orderPanel(Object obj){
        JComboBox<String> Item = (JComboBox<String>) obj;
        ArrayList<Person> per=null;
        if(Item.getSelectedItem().equals("姓名")){
            try {
                per=SearchQuery.queryAllByName();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,"加载失败！"+e.getMessage());
            }
        } else if (Item.getSelectedItem().equals("电话号码")) {
            try {
                per=SearchQuery.queryAllByNumber();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,"加载失败！"+e.getMessage());
            }
        }
        updatePanel(per);
    }

    private void initFunction() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0, 0, 586, 20);
        JLabel welcome = new JLabel("欢迎使用");
        welcome.setBounds(0, 0, 80, 20);
        panel.add(welcome);

        JLabel orderWay=new JLabel("排序方式");
        orderWay.setBounds(95,0,80,20);
        this.add(orderWay);

        orderBox = new JComboBox<>();
        orderBox.addItem("姓名");
        orderBox.addItem("电话号码");
        orderBox.setBounds(150,0,80,20);
        orderBox.addActionListener(this);
        panel.add(orderBox);

        update = new JButton("刷新");
        update.setBounds(263, 0, 60, 20);
        update.addActionListener(this);
        panel.add(update);

        Text = new JTextField();
        Text.setBounds(425, 0, 100, 20);
        panel.add(Text);

        searchEnsure = new JButton("搜索");
        searchEnsure.setBounds(525, 0, 60, 20);
        searchEnsure.addActionListener(this);
        panel.add(searchEnsure);

        comboBox = new JComboBox<>();
        comboBox.addItem("姓名");
        comboBox.addItem("电话号码");
        comboBox.setBounds(345, 0, 80, 20);
        comboBox.addActionListener(this);
        panel.add(comboBox);
        this.add(panel);
    }

    private void addPerson() {
        Dialog = new JDialog();
        //取消默认布局
        Dialog.setLayout(null);

        Dialog.setTitle("添加联系人");
        Dialog.setSize(270, 190);

        JLabel labName = new JLabel("姓名:");
        JLabel labSex = new JLabel("性别:");
        JLabel labNumber = new JLabel("电话号码:");
        JLabel labAddress = new JLabel("家庭住址:");

        labName.setBounds(20, 20, 60, 20);
        labSex.setBounds(20, 40, 60, 20);
        labNumber.setBounds(20, 60, 60, 20);
        labAddress.setBounds(20, 80, 60, 20);

        Dialog.add(labName);
        Dialog.add(labSex);
        Dialog.add(labNumber);
        Dialog.add(labAddress);

        name = new JTextField();
        sex = new JTextField();
        number = new JTextField();
        address = new JTextField();

        name.setBounds(110, 20, 120, 20);
        sex.setBounds(110, 40, 120, 20);
        number.setBounds(110, 60, 120, 20);
        address.setBounds(110, 80, 120, 20);

        Dialog.add(name);
        Dialog.add(sex);
        Dialog.add(number);
        Dialog.add(address);

        addEnsure = new JButton("确定");
        addEnsure.setBounds(100, 110, 60, 30);
        Dialog.add(addEnsure);
        addEnsure.addActionListener(this);

        Dialog.setLocationRelativeTo(null);
        //不关闭弹窗无法操作下面的页面
        Dialog.setModal(true);
        Dialog.setVisible(true);
    }

    private void addPersonEnsure() {

        if (name.getText().isEmpty() || sex.getText().isEmpty() || number.getText().isEmpty() || address.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "输入信息不完整！");
        } else if (number.getText().length() != 11) {
            JOptionPane.showMessageDialog(null, "号码输入有误！");
        } else {
            //判断联系人是否已存在
            try {
                if (AddUpdate.checkNumber(number.getText())) {
                    JOptionPane.showMessageDialog(null, "该号码已存在！");
                    return;
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "查询失败" + e.getMessage());
            }
            //写入
            try {
                AddUpdate.add(name.getText(), sex.getText(), number.getText(), address.getText());
                JOptionPane.showMessageDialog(null, "添加成功！");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "添加失败：" + e.getMessage());
            }
            Dialog.dispose();
        }

    }

    private void deletePerson() {
        Dialog = new JDialog();
        Dialog.setTitle("删除联系人");
        Dialog.setSize(300, 160);
        Dialog.setLocationRelativeTo(null);
        Dialog.setLayout(null);

        JLabel label = new JLabel("请输入你要删除的联系人的姓名或电话号码:");
        label.setBounds(0, 0, 250, 20);
        Dialog.add(label);

        Text = new JTextField();
        Text.setBounds(60, 25, 150, 20);
        Dialog.add(Text);

        option1 = new JRadioButton("姓名");
        option2 = new JRadioButton("电话号码");
        option1.setBounds(57, 50, 50, 20);
        option2.setBounds(134, 50, 100, 20);
        group = new ButtonGroup();
        group.add(option1);
        group.add(option2);
        Dialog.add(option1);
        Dialog.add(option2);

        deleteEnsure = new JButton("确定");
        deleteEnsure.setBounds(110, 80, 60, 30);
        deleteEnsure.addActionListener(this);
        Dialog.setModal(true);
        Dialog.add(deleteEnsure);

        Dialog.setVisible(true);
    }

    private void deletePersonEnsure() {
        if (Text.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "请输入姓名或者号码！");
        } else if (group.getSelection() == null) {
            JOptionPane.showMessageDialog(null, "请选择输入方式！");
        } else {
            if (option1.isSelected()) {
                //以姓名匹配删除
                try {
                    if (DeleteUpdate.byName(Text.getText()) == 0) {//影响行数为0
                        JOptionPane.showMessageDialog(null, "该联系人不存在！");
                    } else {
                        JOptionPane.showMessageDialog(null, "删除成功！");
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "删除失败！" + e.getMessage());
                }
            } else if (option2.isSelected()) {
                //以号码匹配删除
                try {
                    if (DeleteUpdate.byNumber(Text.getText()) == 0) {
                        JOptionPane.showMessageDialog(null, "该联系人不存在！");
                    } else {
                        JOptionPane.showMessageDialog(null, "删除成功！");
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "删除失败！" + e.getMessage());
                }
            }
            //关闭对话框
            Dialog.dispose();
        }
    }

    private void searchPerson() {
        ArrayList<Person> per = new ArrayList<>();
        if (Objects.equals(comboBox.getSelectedItem(), "姓名")) {
            try {
                per = SearchQuery.queryByName(Text.getText());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,"查询失败！"+e.getMessage());
            }
        } else if (Objects.equals(comboBox.getSelectedItem(), "电话号码")) {
            try {
                per = SearchQuery.queryByNumber(Text.getText());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,"查询失败！"+e.getMessage());
            }
        }
        updatePanel(per);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        if (obj == searchEnsure) {
            searchPerson();
        } else if (obj == Add) {
            addPerson();
        } else if (obj == Delete) {
            deletePerson();
        }  else if (obj == addEnsure) {
            addPersonEnsure();
        } else if (obj == deleteEnsure) {
            deletePersonEnsure();
        }  else if (obj == update) {
            initPanel();
        } else if (obj==orderBox) {
            orderPanel(obj);
        }
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        if(e.getType()==TableModelEvent.UPDATE){
            int row=e.getFirstRow();
            int col=e.getColumn();
            //获取更改后的字符串
            String changedString=(String)model.getValueAt(row,col);
            //获取更改的那一行的号码(修改前的号码)
            String number=data[row][2];
            try{
                if(col==0){
                    ChangeUpdate.changeName(changedString,number);
                }else if(col==1){
                    ChangeUpdate.changeSex(changedString,number);
                } else if (col==2) {
                    ChangeUpdate.changeNumber(changedString,number);
                }else if(col==3){
                    ChangeUpdate.changeAddress(changedString,number);
                }
            } catch (Exception ex){
                JOptionPane.showMessageDialog(null,"修改失败！"+ex.getMessage());
            }
        }
    }
}
