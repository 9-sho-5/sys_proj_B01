export const postMusic = async (Music) => {
  const query_params = new URLSearchParams(Music);
  const url = `/isp2/billboard/add_track?${query_params}`;
  const param = {
    method: "POST",
  };
  const response = await fetch(url, param);
  console.log(response);
  if (response.ok) return true;
  else return false;
};
