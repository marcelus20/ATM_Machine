import React from "react";
import { Progress, Space, Typography, Divider } from "antd";
import { Row, Col } from "antd";

const { Title } = Typography;

const TOTAL_BANK_NOTES = {
  FIFTIES: 10,
  TWENTIES: 30,
  TENNERS: 30,
  FIVERS: 20,
  TOTAL: 1500,
};

function Dashboard({ fifties, fivers, tenners, total, twenties }) {
  const calculatePercentage = (amount, total_bank_notes) => {
    return (amount / total_bank_notes) * 100;
  };

  return (
    <div
      style={{
        maxWidth: "500px",
        margin: "auto",
        height: "auto",
        marginTop: "5vh",
        border: "4px solid lightgrey",
        borderRadius: "8px",
        boxShadow:"3px 3px 3px 3px grey"
      }}
    >
      {
        <Divider style={{marginTop:"50px"}}>
          Amount of Bank Notes in ATM
        </Divider>
      }
      <Row justfify={"center"}>
        <Col span={12}>
          <Progress
            strokeColor={"lightorange"}
            style={{ margin: "20px 50px 20px 50px" }}
            type="circle"
            percent={calculatePercentage(fifties, TOTAL_BANK_NOTES.FIFTIES)}
            format={() => `€50: ${fifties}`}
          />
        </Col>
        <Col span={12}>
          <Progress
            strokeColor={"lightblue"}
            style={{ margin: "20px 50px 20px 50px" }}
            type="circle"
            percent={calculatePercentage(twenties, TOTAL_BANK_NOTES.TWENTIES)}
            format={() => `€20: ${twenties}`}
          />
        </Col>
      </Row>
      <Row>
        <Col span={12}>
          <Progress
            strokeColor={"lightpink"}
            style={{ margin: "20px 50px 20px 50px" }}
            type="circle"
            percent={calculatePercentage(tenners, TOTAL_BANK_NOTES.TENNERS)}
            format={() => `€10: ${tenners}`}
          />
        </Col>
        <Col span={12}>
          <Progress
            strokeColor={"lightgreen"}
            style={{ margin: "20px 50px 20px 50px" }}
            type="circle"
            percent={calculatePercentage(fivers, TOTAL_BANK_NOTES.FIVERS)}
            format={() => `€5: ${fivers}`}
          />
        </Col>
      </Row>
      <Row>
        <Col span={24}>
          <Progress
            style={{ margin: "20px 180px 20px 180px" }}
            type="circle"
            percent={calculatePercentage(total, TOTAL_BANK_NOTES.TOTAL)}
            format={() => `Total: €${total}`}
          />
        </Col>
      </Row>
    </div>
  );
}

export default Dashboard;
