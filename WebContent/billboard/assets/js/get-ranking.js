export const getRanking = async () => {
  let data = null;
  try {
    const response = await fetch("./ranking");
    data = response.json();
  } catch (error) {
    console.log(error);
  }
  return data;
};
