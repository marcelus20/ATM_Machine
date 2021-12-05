// Next.js API route support: https://nextjs.org/docs/api-routes/introduction
import axios from "axios";

export default async function handler(req, res) {
  try{
    const data = await axios.get("http://atm:8080/status").then(res=>res.data);

    res.status(200).json({ data })
  }catch(e){
    res.send(e)
  }
}
