import Head from "next/head";
import Image from "next/image";
import styles from "../styles/Home.module.css";
import React, { useState, useEffect } from "react";
import axios from "axios";
import Dashboard from "../components/Dashboard";
import { Typography } from "antd";
const { Title } = Typography;
import DashboardModel from "../models/DashboardModel";

const createDefaultDashboard = () => new DashboardModel();

export default function Home() {
  const [updatedDashboard, setUpdatedDashboard] = useState(
    createDefaultDashboard()
  );

  //const  = dashboard;

  useEffect(() => {
    const pullData = async () => {
      return await axios
        .get("http://localhost:3000/api/status")
        .then((res) => res.data);
    };

    setTimeout(async () => {
      const { data: dashboard } = await pullData();
      setUpdatedDashboard(dashboard);
    }, 1000);
  });

  return (
    <div>
      <Head>
        <title>ATM Analytics</title>
        <meta name="ATM Analytics" content="ATM Analytics" />
      </Head>
      <Title level={1} align="center" style={{ marginTop: "50px" }}>
        ATM analytics
      </Title>
      {updatedDashboard ? (
        <Dashboard
          fifties={updatedDashboard.fifties}
          fivers={updatedDashboard.fivers}
          tenners={updatedDashboard.tenners}
          twenties={updatedDashboard.twenties}
          total={updatedDashboard.total}
        />
      ) : (
        <Dashboard
          fifties={fifties}
          fivers={fivers}
          tenners={tenners}
          twenties={twenties}
          total={total}
        />
      )}
      <footer className={styles.footer}>
        <a
          href="https://github.com/marcelus20/ATM_Machine"
          target="_blank"
          rel="noopener noreferrer"
        >
          Demo ATM Analytics - Created by Felipe Mantovani
        </a>
      </footer>
    </div>
  );
}
