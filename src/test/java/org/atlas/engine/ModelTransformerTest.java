package org.atlas.engine;

import org.atlas.model.metamodel.Association;
import org.atlas.model.metamodel.Boundary;
import org.atlas.model.metamodel.Control;
import org.atlas.model.metamodel.Entity;
import org.atlas.model.metamodel.Enumeration;
import org.atlas.model.metamodel.Operation;
import org.atlas.model.metamodel.Parameter;
import org.atlas.model.metamodel.Model;
import org.atlas.model.metamodel.Property;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author andrews
 */
public class ModelTransformerTest {

    private static Model PIM;

    public ModelTransformerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        PIM= new Model();

        Enumeration schoolType = new Enumeration();
        schoolType.setName("SchoolType");
        schoolType.addLiteral("Public");
        schoolType.addLiteral("Private");
        PIM.addEnumeration(schoolType);

        Entity school = new Entity();
        school.setName("School");
        Property schoolName = new Property();
        schoolName.setName("name");
        schoolName.setType("string");
        school.addProperty(schoolName);
        Property type = new Property();
        type.setName("type");
        type.setType("SchoolType");
        school.addProperty(type);
        PIM.addEntity(school);

        Entity dept=new Entity();
        dept.setName("Department");
        Property deptName = new Property();
        deptName.setName("name");
        deptName.setType("string");
        dept.addProperty(deptName);
        PIM.addEntity(dept);

        school.addAssociation(Association.Multiplicity.OneToMany, dept, "child", "parent", true);
        dept.addAssociation(Association.Multiplicity.OneToMany, school, "parent", "child", false);

        Entity course = new Entity();
        course.setName("Course");
        Property courseName = new Property();
        courseName.setName("name");
        courseName.setType("string");
        course.addProperty(courseName);
        PIM.addEntity(course);

        Entity person = new Entity();
        person.setName("Person");
        person.setAbstractEntity(true);
        Property personFirst = new Property();
        personFirst.setName("firstName");
        personFirst.setType("string");
        person.addProperty(personFirst);
        Property personLast = new Property();
        personLast.setName("lastName");
        personLast.setType("string");
        person.addProperty(personLast);
        PIM.addEntity(person);


        Entity instr = new Entity();
        instr.setName("Instructor");
        instr.setGeneralization(person);
        Property instrDegree = new Property();
        instrDegree.setName("degree");
        instrDegree.setType("string");
        instr.addProperty(instrDegree);
        PIM.addEntity(instr);

        course.addAssociation(Association.Multiplicity.ManyToOne, instr);

        Entity student = new Entity();
        student.setName("Student");
        student.setGeneralization(person);
        Property studentNum = new Property();
        studentNum.setName("number");
        studentNum.setType("string");
        student.addProperty(studentNum);
        PIM.addEntity(student);

        school.addAssociation(Association.Multiplicity.ManyToMany, student, true);
        student.addAssociation(Association.Multiplicity.ManyToMany, school);

        Entity user = new Entity();
        user.setName("User");
        Property username = new Property();
        username.setName("username");
        username.setType("string");
        user.addProperty(username);
        PIM.addEntity(user);

        student.addAssociation(Association.Multiplicity.OneToOne, user, true);
        user.addAssociation(Association.Multiplicity.OneToOne, student);

        Control studentSvc = new Control();
        studentSvc.setName("SchoolService");
        Operation op1 = new Operation();
        op1.setName("addDepartment");
        op1.setDocumentation("Add a new department to a school.");
        op1.setReturnType("void");
        Parameter op1p1 = new Parameter();
        op1p1.setType("School");
        op1.addParameter(op1p1);
        Parameter op1p2 = new Parameter();
        op1p2.setType("Department");
        op1.addParameter(op1p2);
        studentSvc.addOperation(op1);

        Operation op2 = new Operation();
        op2.setName("registerForOnlineAccess");
        op2.setDocumentation("Register a student to access the school online.");
        op2.setReturnType("User");
        Parameter op2p1 = new Parameter();
        op2p1.setName("student");
        op2p1.setType("Student");
        op2.addParameter(op2p1);
        Parameter op2p2 = new Parameter();
        op2p2.setName("username");
        op2p2.setType("string");
        op2.addParameter(op2p2);
        Parameter op2p3 = new Parameter();
        op2p3.setName("password");
        op2p3.setType("string");
        op2.addParameter(op2p3);
        studentSvc.addOperation(op2);

        PIM.addControl(studentSvc);

        Boundary studentView = new Boundary();
        studentView.setName("StudentView");

        Property svNumber = new Property();
        svNumber.setName("number");
        svNumber.setType("string");
        studentView.addProperty(svNumber);
        Property svFirstName = new Property();
        svFirstName.setName("firstName");
        svFirstName.setType("string");
        studentView.addProperty(svFirstName);
        Property svLastName = new Property();
        svLastName.setName("lastName");
        svLastName.setType("string");
        studentView.addProperty(svLastName);

        Operation addOp = new Operation();
        addOp.setName("addStudent");
        addOp.setReturnType("void");
        studentView.addOperation(addOp);

        PIM.addBoundary(studentView);


        Context.getModelManager().setModel(PIM);
    }
    
    @Test
    public void testTransform() throws TransformException {
        ModelTransformer pt = new ModelTransformer();
        pt.transform();
    }

}