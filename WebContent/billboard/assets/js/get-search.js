export const getSearch = async (keyword) => {
  let data = null;
  try {
    const response = await fetch(`./search?keyword=${keyword}`);
    data = response.json();
  } catch (error) {
    console.log(error);
  }
  return data;
};
